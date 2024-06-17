package cloud.client.cloudClient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("prod")
public class ProductionConfiguration {
    public String productionSpecificFunction() {
        try {
            return "This is a production specific function.";
        } catch (Exception e) {
            return "An error occurred during execution of the production specific function.";
        }
    }
}
