package resmed.hi.demo.rules.patients;

import lombok.Builder;
import lombok.Data;
import resmed.hi.demo.rules.Operator;
import resmed.hi.demo.rules.RuleType;
import resmed.hi.demo.rules.ThresholdType;

@Data
@Builder
public class RuleInput {
    private final boolean enabled;
    private final int period;
    private final Integer program;
    private final RuleType ruleType;
    private final Double threshold;
    private final ThresholdType thresholdType;
    private final Operator operator;
}
