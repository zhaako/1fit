package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Abonement;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.VisitHistory;
import cloud.client.cloudClient.model.roles.DurationAbonement;
import cloud.client.cloudClient.model.roles.Role;
import cloud.client.cloudClient.repository.AbonementRepository;
import cloud.client.cloudClient.service.AbonementService;
import cloud.client.cloudClient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class AbonementController {

    private final AbonementService abonementService;

    private final AbonementRepository abonementRepository;

    private final JwtService jwtService;

    private final UserService userService;

    @PostMapping("/create")
    public Abonement createSubscription(@RequestParam Long userId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return abonementService.createSubscription(userId, startDate, endDate);
    }

    private static final Map<DurationAbonement, Integer> DURATION_MAP = new EnumMap<>(DurationAbonement.class);

    static {
        DURATION_MAP.put(DurationAbonement.oneMonth, 1);
        DURATION_MAP.put(DurationAbonement.threeMonth, 3);
        DURATION_MAP.put(DurationAbonement.sixMonth, 6);
        DURATION_MAP.put(DurationAbonement.twelveMonth, 12);
    }
    @PutMapping("/renew/{id}")
    public Abonement renewSubscription(@PathVariable Long id, @RequestBody Abonement abonement) {
        Optional<Abonement> optionalAbonement = abonementRepository.findById(id);
        if (optionalAbonement.isEmpty()) {
            throw new RuntimeException("Abonement not found with id: " + id);
        }

        Abonement ab = optionalAbonement.get();
        ab.setStartDate(LocalDateTime.now());

        DurationAbonement durationAbonement = abonement.getAbonement();
        Integer duration = DURATION_MAP.get(durationAbonement);
        if (duration != null) {
            ab.setEndDate(LocalDateTime.now().plusMonths(duration));
            ab.setAbonement(durationAbonement);
        } else {
            throw new IllegalArgumentException("Unsupported duration abonement: " + durationAbonement);
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
    public Object addAbonement(@RequestBody Abonement abonement, @AuthenticationPrincipal User userDetails){
        Long userId = jwtService.extractId(jwtService.generateToken(userDetails));
        User user = userService.findUser(userId);
        if(userDetails.getRole()==Role.USER){
            abonement.setUser(user);
            abonement.setEndDate(abonement.getStartDate().plusMonths(1));
            return abonementService.addAbonement(abonement);
        }
        else {
            return "only User can get abonement";
        }
    }

    @GetMapping("/users/{userId}/hasAbonement")
    public boolean hasActiveAbonement(@PathVariable Long userId) {
        return abonementService.hasActiveAbonement(userId);
    }
}
