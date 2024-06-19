package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.dto.CoachDto;
import cloud.client.cloudClient.model.dto.NewLessonDto;
import cloud.client.cloudClient.repository.CoachRepository;
import cloud.client.cloudClient.service.CoachService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            List<NewLessonDto> newlessonDtos = new ArrayList<>();
            for(Lesson lesson : coach.getLessons()){
                NewLessonDto newLessonDto = new NewLessonDto();
                newLessonDto.setLessonName(lesson.getLessonName());
                newLessonDto.setLessonPrice(lesson.getLessonPrice());
                newlessonDtos.add(newLessonDto);
            }
            coachDto.setLessons(newlessonDtos);
            needCoachDto.add(coachDto);
        }
        return needCoachDto;
    }
}
