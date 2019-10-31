package resmed.hi.demo.rules;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import resmed.hi.demo.rules.alerts.RulesOutcome;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@Slf4j
public class RuleServiceTest {

    @Autowired
    private RuleService sut;

    @Test
    public void shouldEvaluate() {
        RulesOutcome outcome = sut.score(getData());
        log.info("[Outcome: {}]", outcome);
        // assertThat("Expected exception is not raised", outcome.getExceedingAhiSerialNumbers(), contains("hello"));
    }

    private Map<String, Double> getData() {
        Map<String, Double> data = new HashMap<>();
        data.put("Val.AHI", 16.0);
        return data;
    }
}
