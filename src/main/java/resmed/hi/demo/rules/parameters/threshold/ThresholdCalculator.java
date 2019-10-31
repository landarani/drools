package resmed.hi.demo.rules.parameters.threshold;

import resmed.hi.demo.rules.Operator;

import java.time.LocalDate;
import java.util.Optional;

public interface ThresholdCalculator {
    default Optional<Double> calculate(Optional<Double> value, Operator operator, LocalDate sessionDate) {
        return value;
    }
}
