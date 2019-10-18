package resmed.hi.demo.rules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class BriscoeRulesApplication {

    protected BriscoeRulesApplication() {}

    public static void main(String[] args) {
        SpringApplication.run(BriscoeRulesApplication.class, args);
    }

    @Configuration
    @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        }
    }

    @Bean
    public Docket mdsApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(new ApiInfoBuilder()
            .title("Briscoe Rules Demo")
            .description("Demo Application for Briscoe Rules Demo")
            .build())
            .enable(true)
            .select()
            .paths(regex("/*"))
            .build();
        return docket;
    }

}
