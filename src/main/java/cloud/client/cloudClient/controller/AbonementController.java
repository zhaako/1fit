package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Abonement;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.VisitHistory;
import cloud.client.cloudClient.model.roles.DurationAbonement;
import cloud.client.cloudClient.repository.AbonementRepository;
import cloud.client.cloudClient.service.AbonementService;
import cloud.client.cloudClient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class AbonementController {
    @Autowired
    private AbonementService abonementService;
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Abonement createSubscription(@RequestParam Long userId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return abonementService.createSubscription(userId, startDate, endDate);
    }

    @PutMapping("/renew/{id}")
    public Abonement renewSubscription(@PathVariable Long id, @RequestBody Abonement abonement) {
        Optional<Abonement> optionalAbonement = abonementRepository.findById(id);
        if (!optionalAbonement.isPresent()) {
            throw new RuntimeException("Abonement not found with id: " + id);
        }
        Abonement ab = optionalAbonement.get();
        ab.setStartDate(LocalDateTime.now());
        if(abonement.getAbonement().equals(DurationAbonement.oneMonth)){
            ab.setEndDate(LocalDateTime.now().plusMonths(1));
            ab.setAbonement(abonement.getAbonement());
        }
        else if(abonement.getAbonement().equals(DurationAbonement.threeMonth)){
            ab.setEndDate(LocalDateTime.now().plusMonths(3));
            ab.setAbonement(abonement.getAbonement());
        }
        else if(abonement.getAbonement().equals(DurationAbonement.sixMonth)){
            ab.setEndDate(LocalDateTime.now().plusMonths(6));
            ab.setAbonement(abonement.getAbonement());
        }
        else if(abonement.getAbonement().equals(DurationAbonement.twelveMonth)){
            ab.setEndDate(LocalDateTime.now().plusMonths(12));
            ab.setAbonement(abonement.getAbonement());
        }
        return abonementService.renewSubscription(ab);
    }

    @GetMapping("/{abonementId}/visits")
    public List<VisitHistory> getVisitHistory(@PathVariable Long abonementId) {
        return abonementService.getVisitHistory(abonementId);
    }

    @PostMapping("/{abonementId}/visit")
    public VisitHistory addVisit(@PathVariable Long abonementId, @RequestParam LocalDateTime visitDate) {
        return abonementService.addVisit(abonementId, visitDate);
    }

    @PostMapping("/add")
    public Abonement addAbonement(@RequestBody Abonement abonement, @AuthenticationPrincipal User userDetails){
        Long userId = jwtService.extractId(jwtService.generateToken(userDetails));
        User user = userService.findUser(userId);
        abonement.setUser(user);
        return abonementService.addAbonement(abonement);
    }

    @GetMapping("/users/{userId}/hasAbonement")
    public boolean hasActiveAbonement(@PathVariable Long userId) {
        return abonementService.hasActiveAbonement(userId);
    }
}
