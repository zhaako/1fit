package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.comp.ResourceNotFoundException;
import cloud.client.cloudClient.model.Schedule;
import cloud.client.cloudClient.model.dto.ScheduleDto;
import cloud.client.cloudClient.repository.ScheduleRepository;
import cloud.client.cloudClient.service.ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<ScheduleDto> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDto> viewSchedule = new ArrayList<>();
        for (Schedule schedule : schedules){
            ScheduleDto viewShed = new ScheduleDto();
            viewShed.setUsername(schedule.getUser().getName());
            viewShed.setLesson(schedule.getLesson());
            viewShed.setDate(schedule.getDate());
            viewShed.setDuration(schedule.getDuration());
            viewShed.setStartTime(schedule.getStartTime());
            viewSchedule.add(viewShed);
        }
        return viewSchedule;
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        schedule.setDate(updatedSchedule.getDate());
        schedule.setStartTime(updatedSchedule.getStartTime());
        schedule.setDuration(updatedSchedule.getDuration());

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesByUserId(Long userId) {
        return null;
    }



}