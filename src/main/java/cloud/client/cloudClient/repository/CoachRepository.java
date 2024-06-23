package cloud.client.cloudClient.repository;


import cloud.client.cloudClient.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Coach findCoachById(Long id);
}
