package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.dto.CoachDto;
import cloud.client.cloudClient.model.dto.NewLessonDto;
import cloud.client.cloudClient.repository.CoachRepository;
import cloud.client.cloudClient.repository.UserRepository;
import cloud.client.cloudClient.service.CoachService;
import cloud.client.cloudClient.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LessonService lessonService;

    public CoachServiceImpl() {
    }

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
    @Transactional
    public Coach saveCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public List<CoachDto> getAllNeedCoach() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachDto> needCoachDto = new ArrayList<>();
        for(Coach coach : coaches){
            CoachDto coachDto = new CoachDto();
            coachDto.setName(coach.getName());
            coachDto.setPosition(coach.getPosition());
            coachDto.setDescription(coach.getDescription());
            coachDto.setUsername(coach.getUsername());
            List<NewLessonDto> newlessonDtos = lessonService.findLessonsById(coach.getId());
            coachDto.setLessons(newlessonDtos);
            needCoachDto.add(coachDto);
        }
        return needCoachDto;
    }

    @Override
    public Optional<Coach> getCoachById(Long id) {
        return coachRepository.findById(id);
    }

    @Override
    public Coach getCoach(Long id) {
        return coachRepository.findCoachById(id);
    }


}
