package cloud.client.cloudClient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class DeveloperConfiguration{
    public String devSpecificFunction() {
        return "This is a production specific function.";
    }
}