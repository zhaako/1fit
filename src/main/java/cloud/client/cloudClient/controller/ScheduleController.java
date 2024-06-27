package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.Schedule;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.dto.ScheduleDto;
import cloud.client.cloudClient.service.LessonService;
import cloud.client.cloudClient.service.ScheduleService;
import cloud.client.cloudClient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final LessonService lessonService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        try {
            Schedule savedSchedule = scheduleService.createSchedule(schedule);
            return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getSchedules();
    }
    @GetMapping("/all")
    public List<ScheduleDto> getAllScheduleDTO(){
        return scheduleService.getAllSchedule();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {
        Schedule schedule = scheduleService.updateSchedule(id, updatedSchedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<Schedule> getSchedulesByUserId(@PathVariable Long userId) {
        return scheduleService.getSchedulesByUserId(userId);
    }

    @PostMapping("/addLesson/{id}")
    public Schedule addLesson(@PathVariable Long id, @RequestBody Schedule schedule, @AuthenticationPrincipal User userDetails){
        Long userId = jwtService.extractId(jwtService.generateToken(userDetails));
        User user = userService.findUser(userId);
        Lesson lesson = lessonService.findLesson(id);
        schedule.setUser(user);
        schedule.setLesson(List.of(lesson));
        return scheduleService.createSchedule(schedule);
    }
}
