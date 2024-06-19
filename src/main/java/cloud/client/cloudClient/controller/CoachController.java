package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.dto.CoachDto;
import cloud.client.cloudClient.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/coach")
public class CoachController {
    @Autowired
    private CoachService coachService;
    @GetMapping
    public List<Coach> getAllCoach(){
        return coachService.getAllCoach();
    }

    @GetMapping("/Dto")
    public List<CoachDto> getAllNeedCoach(){
        return coachService.getAllNeedCoach();
    }
}
