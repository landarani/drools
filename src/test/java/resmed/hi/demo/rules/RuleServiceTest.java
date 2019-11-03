package resmed.hi.demo.rules;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import resmed.hi.demo.rules.alerts.RulesOutcome;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@Slf4j
public class RuleServiceTest {

    @Autowired
    private RuleService sut;

    @Test
    public void shouldEvaluateWitData() throws Exception {
        RulesOutcome outcome = sut.scorePatient("Tes-Patient", Optional.empty()).get();
        log.info("============== RAISED ALERTS ==============");
        outcome.getRaised().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("============== CORRECTED ALERTS ==============");
        outcome.getCorrected().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("-------------------------------------------------------------------\n");
    }

    @Test
    public void shouldEvaluateNoData() throws Exception {
        RulesOutcome outcome = sut.scorePatient("No-Data", Optional.empty()).get();
        log.info("============== RAISED ALERTS ==============");
        outcome.getRaised().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("============== CORRECTED ALERTS ==============");
        outcome.getCorrected().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("-------------------------------------------------------------------\n");
    }

    @Test
    public void shouldEvaluateNoDataOfOlderDates() throws Exception {
        // LOW Pressure shouldn't raise as it it happening only in the last 3 days
        RulesOutcome outcome = sut.scorePatient("DELAYED-Patients", Optional.of(LocalDate.now().minusDays(4))).get();
        log.info("============== RAISED ALERTS ==============");
        outcome.getRaised().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("============== CORRECTED ALERTS ==============");
        outcome.getCorrected().forEach((e, s) -> log.info("[{}] ==> [{}]", e, s));
        log.info("-------------------------------------------------------------------\n");
    }
}
