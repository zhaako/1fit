package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.Abonement;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.VisitHistory;
import cloud.client.cloudClient.repository.AbonementRepository;
import cloud.client.cloudClient.repository.UserRepository;
import cloud.client.cloudClient.repository.VisitHistoryRepository;
import cloud.client.cloudClient.service.AbonementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AbonementServiceImpl implements AbonementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private VisitHistoryRepository visitHistoryRepository;

    @Override
    public Abonement createSubscription(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findUserById(userId);
        Abonement subscription = new Abonement();
        subscription.setUser(user);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        return abonementRepository.save(subscription);
    }

    @Override
    public Abonement renewSubscription(Abonement abonement) {
        return abonementRepository.save(abonement);
    }

    @Override
    public List<VisitHistory> getVisitHistory(Long abonementId) {
        return visitHistoryRepository.findByAbonementId(abonementId);
    }

    @Override
    public VisitHistory addVisit(Long abonementId, LocalDateTime visitDate) {
        Abonement subscription = abonementRepository.findById(abonementId).orElseThrow(() -> new RuntimeException("Subscription not found"));
        VisitHistory visitHistory = new VisitHistory();
        visitHistory.setAbonement(subscription);
        visitHistory.setVisitDate(visitDate);
        return visitHistoryRepository.save(visitHistory);
    }

    @Override
    public Abonement addAbonement(Abonement abonement) {
        return abonementRepository.save(abonement);
    }

    @Override
    public boolean hasActiveAbonement(Long userId) {
        return abonementRepository.existsByUserIdAndEndDateAfter(userId, LocalDateTime.now());
    }
}
