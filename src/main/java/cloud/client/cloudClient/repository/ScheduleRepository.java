package cloud.client.cloudClient.repository;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.Schedule;
import cloud.client.cloudClient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);
}
