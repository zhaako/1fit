package cloud.client.cloudClient.service;

import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.Schedule;
import cloud.client.cloudClient.model.User;

import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);
    List<Schedule> getSchedules();

    Schedule updateSchedule(Long id, Schedule updatedSchedule);
    void deleteSchedule(Long id);

    List<Schedule> getSchedulesByUserId(Long userId);
}
