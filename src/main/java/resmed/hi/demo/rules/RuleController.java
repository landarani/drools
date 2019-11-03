package resmed.hi.demo.rules;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import resmed.hi.demo.rules.alerts.RulesOutcome;

@RestController
@RequestMapping("/score")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @GetMapping
    @ApiOperation("Receives the data, evaluates rule against it and returns the current devices in exception")
    public RulesOutcome addSummaryData(@ApiParam @RequestParam("ecn") String ecn,
            @ApiParam @RequestParam(name = "scoreDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> scoreDate)
            throws Exception {
        return ruleService.scorePatient(ecn, scoreDate).get();
    }
}
