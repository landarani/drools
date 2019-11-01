package resmed.hi.demo.rules.patients;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import resmed.hi.demo.rules.CalendarService;
import resmed.hi.demo.rules.Operator;
import resmed.hi.demo.rules.RuleType;
import resmed.hi.demo.rules.ThresholdType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private CalendarService calendarService;

    public Patient getPatient(String ecn) {
        LocalDate today = calendarService.today();
        return new Patient(ecn, null).addDevice(today.minusDays(365), today, new Device("12345678901", 30, 7));
    }

    public List<Patient> getBriscoePatients() {
        return Collections.singletonList(getPatient("ONLY_PATIENT"));
    }

    public List<RuleInput> getPatientRuleParameters(String ecn) {
        RuleInput noData = RuleInput.builder().enabled(false).period(21).thresholdType(ThresholdType.SIMPLE).threshold(50d)
            .operator(Operator.ABOVE).ruleType(RuleType.DATA).build();
        RuleInput lowUsage = RuleInput.builder().enabled(true).period(8).thresholdType(ThresholdType.SIMPLE).threshold(4d)
            .operator(Operator.ABOVE).ruleType(RuleType.USAGE).build();
        RuleInput highAhi = RuleInput.builder().enabled(true).period(9).thresholdType(ThresholdType.SIMPLE).threshold(50d)
            .operator(Operator.ABOVE).ruleType(RuleType.AHI).build();
        RuleInput highPressure = RuleInput.builder().enabled(true).period(3).thresholdType(ThresholdType.USUAL_VALUES).threshold(20d)
            .operator(Operator.ABOVE).ruleType(RuleType.PRESSURE).build();
        RuleInput lowPressure = RuleInput.builder().enabled(true).period(3).thresholdType(ThresholdType.USUAL_VALUES).threshold(20d)
            .operator(Operator.BELOW).ruleType(RuleType.PRESSURE).build();
        return Lists.newArrayList(highAhi, noData, highPressure, lowPressure, lowUsage);
    }

}
