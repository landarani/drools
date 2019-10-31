package resmed.hi.demo.rules.alerts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import resmed.hi.demo.rules.Operator;
import resmed.hi.demo.rules.RuleType;

@Getter
@RequiredArgsConstructor
public enum AlertType {
        HIGH_LEAK(Operator.ABOVE, RuleType.LEAK),
        HIGH_AHI(Operator.ABOVE, RuleType.AHI),
        LOW_USAGE(Operator.BELOW, RuleType.USAGE),
        HIGH_PRESSURE(Operator.ABOVE, RuleType.PRESSURE),
        LOW_PRESSURE(Operator.BELOW, RuleType.PRESSURE),
        NOT_USED(null, RuleType.DATA);
    private final Operator operator;
    private final RuleType ruleType;

    public static AlertType of(Operator operator, RuleType ruleType) {
        for (AlertType type : values()) {
            if (type.operator == operator && type.ruleType == ruleType) {
                return type;
            }
        }
        return null;
    }
}
