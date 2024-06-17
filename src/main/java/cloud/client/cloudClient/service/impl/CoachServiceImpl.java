package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.repository.CoachRepository;
import cloud.client.cloudClient.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Override
    public List<Coach> getAllCoach() {
        return coachRepository.findAll();
    }

    @Override
    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Coach coach) {
        coachRepository.delete(coach);
    }

    @Override
    public Coach saveCoach(Coach coach) {
        return coachRepository.save(coach);
    }
}
