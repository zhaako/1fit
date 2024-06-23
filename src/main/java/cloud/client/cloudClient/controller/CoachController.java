package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.dto.CoachDto;
import cloud.client.cloudClient.model.roles.Role;
import cloud.client.cloudClient.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/coach")
@RequiredArgsConstructor
public class CoachController {
    @Autowired
    private CoachService coachService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoach(@PathVariable Long id, @RequestBody CoachDto coachDto, @AuthenticationPrincipal User userDetails){
        Optional<Coach> optionalCoach = coachService.getCoachById(id);
        Long currentId = jwtService.extractId(jwtService.generateToken(userDetails));
        if (optionalCoach.isPresent()) {
            if(Objects.equals(id, currentId) && userDetails.getRole().equals(Role.COACH)){
                Coach coach = optionalCoach.get();
                coach.setName(coachDto.getName());
                coach.setUsername(coachDto.getUsername());
                coach.setPosition(coachDto.getPosition());
                coach.setPassword(passwordEncoder.encode(coachDto.getPassword()));
                coach.setDescription(coach.getDescription());
                coachService.saveCoach(coach);
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You dont have access to change this profile");
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public List<Coach> getAllCoach(){
        return coachService.getAllCoach();
    }

    @GetMapping("/Dto")
    public List<CoachDto> getAllNeedCoach(){
        return coachService.getAllNeedCoach();
    }


}
