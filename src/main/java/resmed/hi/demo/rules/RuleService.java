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
import resmed.hi.demo.rules.patients.Patient;
import resmed.hi.demo.rules.patients.PatientService;
import resmed.hi.demo.rules.patients.RuleInput;

import java.time.LocalDate;
import java.util.List;
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

    @Async
    public RulesOutcome scorePatient(Patient patient, Optional<LocalDate> lastDate) {
        List<RuleInput> inputs = patientService.getPatientRuleParameters(patient);
        int days = inputs.stream().filter(i -> i.isEnabled()).map(
            i -> i.getPeriod() + (i.getThresholdType() == ThresholdType.USUAL_VALUES ? UsualValueService.USUAL_VALUES_DAYS : 0))
            .max(Integer::compare).orElse(0);
        LocalDate endDate = lastDate.orElse(calendarService.today());
        LocalDate startDate = endDate.minusDays(days);
        List<SummaryData> data = summaryDataService.getDataForPatient(patient.getEcn(), startDate, endDate);
        List<RuleParameter> params = inputs.stream().filter(i -> i.isEnabled())
            .map(i -> new RuleParameter(patient, metricResolver, i.getThresholdType == ThresholdType.USUAL_VALUES ? null : new ThresholdCalculator(), i, endDate)).collect(Collectors.toList());
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
}
