package resmed.hi.demo.rules.alerts;

import lombok.Data;
import resmed.hi.demo.rules.parameters.RuleParameter;

import java.util.HashMap;
import java.util.Map;

@Data
public class RulesOutcome {
    private final Map<AlertType, Score> raised = new HashMap<>();
    private final Map<AlertType, Score> corrected = new HashMap<>();

    public void raise(RuleParameter parameter, Double metricValue) {
        raised.put(getType(parameter), new Score(metricValue, parameter.getInput()));
    }

    public void correct(RuleParameter parameter, Double metricValue) {
        corrected.put(getType(parameter), new Score(metricValue, parameter.getInput()));
    }

    private AlertType getType(RuleParameter parameter) {
        return AlertType.of(parameter.getOperator(), parameter.getInput().getRuleType());
    }
}
