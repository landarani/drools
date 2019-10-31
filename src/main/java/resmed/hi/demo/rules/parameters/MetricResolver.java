package resmed.hi.demo.rules.parameters;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;
import resmed.hi.demo.rules.RuleType;
import resmed.hi.demo.rules.patients.Patient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MetricResolver {

    private final Map<RuleType, Map<Integer, String>> midMetrics = new HashedMap<>();
    private final Map<RuleType, String> defaultMetrics = new HashedMap<>();

    public MetricResolver() {
        // default metrics
        defaultMetrics.put(RuleType.AHI, "Val.AHI");
        defaultMetrics.put(RuleType.LEAK, "Val.Leak.50");
        defaultMetrics.put(RuleType.USAGE, "Val.Duration");
        // mid metric overrides
        Map<Integer, String> leakMids = new HashMap<>();
        leakMids.put(27, "Val.LeakVented.50");
        midMetrics.put(RuleType.LEAK, leakMids);
    }

    public String metric(LocalDate session, RuleType ruleType, Patient patient) {
        return Optional.ofNullable(patient.getDeviceAssignment().get(session))
            .flatMap(d -> Optional.ofNullable(midMetrics.get(ruleType))
                .flatMap(m -> Optional.ofNullable(m.get(d.getMid()))))
            .orElse(defaultMetrics.get(ruleType));
    }

}
