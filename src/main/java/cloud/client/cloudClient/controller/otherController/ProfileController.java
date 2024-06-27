package cloud.client.cloudClient.controller.otherController;

import cloud.client.cloudClient.config.DeveloperConfiguration;
import cloud.client.cloudClient.model.MyRecord;
import cloud.client.cloudClient.service.impl.HighLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ProfileController {

    @Value("${spring.profiles.active}")
    private String activeProfile;


    private final DeveloperConfiguration developerConfiguration;
    private final HighLoadService highLoadService;

    @GetMapping("/record/{id}")
    public MyRecord getOrCreate(@PathVariable int id){
        return highLoadService.getOrCreateRecord(id);
    }

    @PutMapping("/record/{id}")
    public MyRecord createOrUpdate(@PathVariable int id){
        return highLoadService.createOrUpdate(id);
    }

    @DeleteMapping("/record/{id}")
    public String deleteRecord(@PathVariable int id){
        highLoadService.deleteRecord(id);
        return "Removed";
    }


    @GetMapping("/active-profile")
    public String getActiveProfile() {
        String dev = developerConfiguration.devSpecificFunction();
        return "Active profile: " + activeProfile + dev;
    }
    
}
