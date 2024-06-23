package cloud.client.cloudClient.service;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.dto.CoachDto;

import java.util.List;
import java.util.Optional;

public interface CoachService {
    List<Coach> getAllCoach();
    Coach addCoach(Coach coach);
    void deleteCoach(Coach coach);
    Coach saveCoach(Coach coach);
    List<CoachDto> getAllNeedCoach();
    Optional<Coach> getCoachById(Long id);
    Coach getCoach(Long id);
}
