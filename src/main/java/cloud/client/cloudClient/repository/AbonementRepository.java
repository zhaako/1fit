package cloud.client.cloudClient.repository;

import cloud.client.cloudClient.model.Abonement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbonementRepository extends JpaRepository<Abonement, Long> {
    List<Abonement> findByUserId(Long id);
    boolean existsByUserIdAndEndDateAfter(Long userId, LocalDateTime endDate);
}