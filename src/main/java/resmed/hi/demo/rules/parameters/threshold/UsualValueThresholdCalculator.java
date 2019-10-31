package resmed.hi.demo.rules.parameters.threshold;

import resmed.hi.demo.rules.Operator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsualValueThresholdCalculator implements ThresholdCalculator {
    Map<LocalDate, Double> thresholds = new HashMap<>();

    public UsualValueThresholdCalculator(Map<LocalDate, Double> thresholds) {
        thresholds.putAll(thresholds);
    }

    @Override
    public Optional<Double> calculate(Optional<Double> value, Operator operator, LocalDate sessionDate) {
        return value.flatMap(
            v -> Optional.ofNullable(thresholds.get(sessionDate)).map(t -> mapAbove(t, v, operator)).map(t -> mapBelow(t, v, operator)));
    }

    private Double mapAbove(Double threshold, Double value, Operator operator) {
        return operator == Operator.ABOVE ? threshold * (1 + value / 100) : value;
    }

    private Double mapBelow(Double threshold, Double value, Operator operator) {
        return operator == Operator.ABOVE ? threshold * (1 - value / 100) : value;
    }
}
