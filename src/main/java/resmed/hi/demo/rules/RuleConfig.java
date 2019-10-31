package resmed.hi.demo.rules;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfig {

    @Bean
    public KieContainer kContainer() {
        KieServices ks = KieServices.Factory.get();
        return ks.getKieClasspathContainer(getClass().getClassLoader());
    }

    @Bean
    public KieContainerSessionsPool kPool(@Value("${demo.rules.pool.size:10}") int size, KieContainer container) {
        return container.newKieSessionsPool(size);
    }
}
