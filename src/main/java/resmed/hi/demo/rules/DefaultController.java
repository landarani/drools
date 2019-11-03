package resmed.hi.demo.rules;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public RedirectView redirectToInfo() {
        return new RedirectView("/swagger-ui.html");
    }
}
