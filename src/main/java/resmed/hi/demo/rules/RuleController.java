package resmed.hi.demo.rules;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resmed.hi.demo.rules.alerts.RulesOutcome;
import resmed.hi.demo.rules.facts.SummaryDataService;

@RestController
@RequestMapping("/summary")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping
    @ApiOperation("Receives the data, evaluates rule against it and returns the current devices in exception")
    public RulesOutcome addSummaryData(@ApiParam SummaryDataService data) {
        return ruleService.score(null);
    }
}