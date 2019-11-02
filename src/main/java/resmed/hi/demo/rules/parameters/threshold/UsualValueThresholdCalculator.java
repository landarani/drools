package resmed.hi.demo.rules.parameters.threshold;

import resmed.hi.demo.rules.Operator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsualValueThresholdCalculator implements ThresholdCalculator {
    private final Map<LocalDate, Double> usualValues = new HashMap<>();

    public UsualValueThresholdCalculator(Map<LocalDate, Double> usualValues) {
        this.usualValues.putAll(usualValues);
    }

    @Override
    public Optional<Double> calculate(Optional<Double> variant, Operator operator, LocalDate sessionDate) {
        return variant.flatMap(
            v -> Optional.ofNullable(usualValues.get(sessionDate)).map(uv -> mapAbove(uv, v, operator)).map(uv -> mapBelow(uv, v, operator)));
    }

    private Double mapAbove(Double usualValue, Double variant, Operator operator) {
        return operator == Operator.ABOVE ? usualValue * (1 + variant / 100) : usualValue;
    }

    private Double mapBelow(Double usualValue, Double variant, Operator operator) {
        return operator == Operator.BELOW ? usualValue * (1 - variant / 100) : usualValue;
    }
}
