package cloud.client.cloudClient.controller.otherController;

import cloud.client.cloudClient.comp.Scheduler;
import cloud.client.cloudClient.config.DeveloperConfiguration;
import cloud.client.cloudClient.config.ProductionConfiguration;
import cloud.client.cloudClient.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProfileController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private ProductionConfiguration productionConfiguration;
    @Autowired
    private DeveloperConfiguration developerConfiguration;

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/active-profile")
    public String getActiveProfile() {
        String dev = developerConfiguration.devSpecificFunction();
        return "Active profile: " + activeProfile + dev;
    }
}
