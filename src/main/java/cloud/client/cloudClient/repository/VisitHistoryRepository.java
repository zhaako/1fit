package cloud.client.cloudClient.repository;

import cloud.client.cloudClient.model.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {
    List<VisitHistory> findByAbonementId(Long subscriptionId);
}