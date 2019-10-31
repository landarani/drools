package resmed.hi.demo.rules;

import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import resmed.hi.demo.rules.alerts.RulesOutcome;
import resmed.hi.demo.rules.facts.SummaryData;
import resmed.hi.demo.rules.facts.SummaryDataService;
import resmed.hi.demo.rules.facts.UsualValueService;
import resmed.hi.demo.rules.parameters.MetricResolver;
import resmed.hi.demo.rules.parameters.RuleParameter;
import resmed.hi.demo.rules.parameters.threshold.ThresholdCalculator;
import resmed.hi.demo.rules.parameters.threshold.UsualValueThresholdCalculator;
import resmed.hi.demo.rules.patients.Patient;
import resmed.hi.demo.rules.patients.PatientService;
import resmed.hi.demo.rules.patients.RuleInput;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuleService {

    @Autowired
    private KieContainerSessionsPool pool;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SummaryDataService summaryDataService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private MetricResolver metricResolver;

    @Autowired
    private UsualValueService usualValueService;

    @Async
    public RulesOutcome scorePatient(Patient patient, Optional<LocalDate> lastDate) {
        List<RuleInput> inputs = patientService.getPatientRuleParameters(patient);
        int days = inputs.stream().filter(i -> i.isEnabled()).map(
            i -> i.getPeriod() + (i.getThresholdType() == ThresholdType.USUAL_VALUES ? UsualValueService.USUAL_VALUES_DAYS : 0))
            .max(Integer::compare).orElse(0);
        int maxPeriod = inputs.stream().filter(i -> i.isEnabled()).map(RuleInput::getPeriod).max(Integer::compare).orElse(0);
        LocalDate endDate = lastDate.orElse(calendarService.today());
        LocalDate startDate = endDate.minusDays(days);
        List<SummaryData> data = summaryDataService.getDataForPatient(patient.getEcn(), startDate, endDate);
        data.sort((d1, d2) -> d2.getSessionDate().compareTo(d1.getSessionDate()));

        List<RuleParameter> params = inputs.stream().filter(i -> i.isEnabled())
            .map(i -> new RuleParameter(patient, metricResolver, buildThresholdCalculator(patient, i, data, endDate), i, endDate))
            .collect(Collectors.toList());
        return score(params, data.stream().limit(maxPeriod).collect(Collectors.toList()));
    }

    private RulesOutcome score(List<RuleParameter> params, List<SummaryData> data) {
        KieSession session = pool.newKieSession("BriscoeRulesKS");
        RulesOutcome outcome = new RulesOutcome();
        session.setGlobal("rulesOutcome", outcome);
        params.stream().forEach(param -> session.insert(param));
        session.insert(data);
        session.fireAllRules();
        session.dispose();
        return outcome;
    }

    private ThresholdCalculator buildThresholdCalculator(Patient patient, RuleInput input, List<SummaryData> data, LocalDate lastDate) {
        if (input.getThresholdType() == ThresholdType.USUAL_VALUES) {
            return new UsualValueThresholdCalculator(data.stream()
                .filter(d -> !d.getSessionDate().isAfter(lastDate) && d.getSessionDate().isAfter(lastDate.minusDays(input.getPeriod())))
                .map(d -> usualValueService.calculateUsualValue(data, d.getSessionDate(), metricResolver, input.getRuleType(), patient)
                    .map(t -> Collections.singletonMap(d.getSessionDate(), t)).orElse(null))
                .filter(Objects::nonNull)
                .reduce(new HashMap<LocalDate, Double>(), (m, m1) -> {
                    m.putAll(m1);
                    return m;
                }));

        }
        return new ThresholdCalculator() {};
    }
}
