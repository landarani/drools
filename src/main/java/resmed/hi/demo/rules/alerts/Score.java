package resmed.hi.demo.rules.alerts;

import lombok.Data;
import resmed.hi.demo.rules.patients.RuleInput;

@Data
public class Score {
    private final Double metricValue;
    private final RuleInput input;
}
