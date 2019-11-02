package resmed.hi.demo.rules.parameters;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import resmed.hi.demo.rules.Operator;
import resmed.hi.demo.rules.parameters.threshold.ThresholdCalculator;
import resmed.hi.demo.rules.patients.Patient;
import resmed.hi.demo.rules.patients.RuleInput;

@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class RuleParameter {

    @Getter
    @ToString.Include
    private final RuleInput input;

    @Getter
    @ToString.Include
    private final LocalDate scoreDate;

    private final Patient patient;
    private final MetricResolver metricResolver;
    private final ThresholdCalculator thresholdCalculator;

    public boolean isEnabled() {
        return input.isEnabled();
    }

    public int getPeriod() {
        return input.getPeriod();
    }

    public Integer getProgram() {
        return input.getProgram();
    }

    public Operator getOperator() {
        return input.getOperator();
    }

    public Double getThreshold(LocalDate session) {
        return thresholdCalculator.calculate(Optional.ofNullable(input.getThreshold()), input.getOperator(), session).orElse(null);
    }

    public String getMetric(LocalDate session) {
        return metricResolver.metric(session, input.getRuleType(), patient);
    }

}
