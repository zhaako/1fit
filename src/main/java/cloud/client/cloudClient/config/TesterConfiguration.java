package cloud.client.cloudClient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("test")
public class TesterConfiguration {
    public void testSpecificFunction() {
        System.out.println("This is a production specific function.");
    }
}