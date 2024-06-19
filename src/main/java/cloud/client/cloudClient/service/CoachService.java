package cloud.client.cloudClient.service;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.dto.CoachDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CoachService {
    List<Coach> getAllCoach();
    Coach addCoach(Coach coach);
    void deleteCoach(Coach coach);
    Coach saveCoach(Coach coach);
    List<CoachDto> getAllNeedCoach();
}
