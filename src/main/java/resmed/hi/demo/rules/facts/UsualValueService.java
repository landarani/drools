package resmed.hi.demo.rules.facts;

import org.springframework.stereotype.Service;
import resmed.hi.demo.rules.RuleType;
import resmed.hi.demo.rules.parameters.MetricResolver;
import resmed.hi.demo.rules.patients.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsualValueService {
    public final static int USUAL_VALUES_DAYS = 90;
    public final static int USUAL_VALUES_MINIMUM_DAYS = 14;

    public Optional<Double> calculateUsualValue(List<SummaryData> data, LocalDate sessionDate, MetricResolver metricResolver,
        RuleType ruleType, Patient patient) {
        List<Double> usualValues = data.stream()
            .filter(
                d -> d.getSessionDate().isBefore(sessionDate)
                    && d.getSessionDate().isAfter(sessionDate.minusDays(USUAL_VALUES_DAYS + 1)))
            .map(d -> d.get(metricResolver.metric(sessionDate, ruleType, patient)))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        int middle = (usualValues.size() + 1) / 2 - 1;
        int limit = 1 + (usualValues.size() + 1) % 2;
        return Optional.of(usualValues.size() < USUAL_VALUES_MINIMUM_DAYS ? null
            : usualValues.stream().sorted().skip(middle).limit(limit).collect(Collectors.averagingDouble(Double::doubleValue)));
    }
}
