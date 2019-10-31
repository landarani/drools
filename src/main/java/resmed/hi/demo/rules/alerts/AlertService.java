package resmed.hi.demo.rules.alerts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resmed.hi.demo.rules.patients.Patient;

@Service
@Slf4j
public class AlertService {

    public void persist(Patient patient, RulesOutcome ruleOutcome) {
        log.info("Persisting outcome: [{}] for patient [{}]", ruleOutcome, patient);
    }
}
