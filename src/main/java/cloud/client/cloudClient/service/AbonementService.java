package cloud.client.cloudClient.service;

import cloud.client.cloudClient.model.Abonement;
import cloud.client.cloudClient.model.VisitHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface AbonementService {
    Abonement createSubscription(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    Abonement renewSubscription(Abonement abonement);
    List<VisitHistory> getVisitHistory(Long abonementId);
    VisitHistory addVisit(Long abonementId, LocalDateTime visitDate);

    Abonement addAbonement(Abonement abonement);
    boolean hasActiveAbonement(Long userId);
}
