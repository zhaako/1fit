package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.comp.ResourceNotFoundException;
import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.Schedule;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.repository.ScheduleRepository;
import cloud.client.cloudClient.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        schedule.setLesson(updatedSchedule.getLesson());
        schedule.setCoach(updatedSchedule.getCoach());
        schedule.setDate(updatedSchedule.getDate());
        schedule.setStartTime(updatedSchedule.getStartTime());
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesByUserId(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }



}