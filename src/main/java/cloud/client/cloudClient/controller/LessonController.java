package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.dto.LessonDto;
import cloud.client.cloudClient.model.dto.NewLessonDto;
import cloud.client.cloudClient.service.CoachService;
import cloud.client.cloudClient.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CoachService coachService;

    @GetMapping
    public List<LessonDto> getAllLesson(){
        return lessonService.getAllLesson();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLesson(@RequestBody NewLessonDto newLessonDto){
        Lesson lesson = new Lesson();
        lesson.setLessonName(newLessonDto.getLessonName());
        lesson.setLessonPrice(newLessonDto.getLessonPrice());
        lessonService.addLesson(lesson);
        return ResponseEntity.ok().body("Lesson added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLesson(@PathVariable Long id, @RequestBody NewLessonDto newLessonDto){
        Lesson lesson = lessonService.findLesson(id);
        lesson.setLessonName(newLessonDto.getLessonName());
        lesson.setLessonPrice(newLessonDto.getLessonPrice());
        lessonService.saveLesson(lesson);
        return ResponseEntity.ok().body("Lesson updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id){
        Lesson lesson = lessonService.findLesson(id);
        lessonService.deleteLesson(lesson);
        return ResponseEntity.ok().body("Lesson deleted successfully");
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> lessonCoach(@PathVariable Long id, @AuthenticationPrincipal User user){
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Need authenticate");
        }
        Coach coach = coachService.getCoach(user.getId());
        Lesson lesson = lessonService.findLesson(id);
        lesson.setCoach(coach);
        lessonService.saveLesson(lesson);
        return ResponseEntity.ok().body("adding lesson to coach successfully");
    }


//    http://localhost:1000/swagger-ui/index.html#/
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadLessons(@RequestParam("file") MultipartFile file) throws IOException {
        CompletableFuture<Void> uploadFuture = lessonService.uploadLesson(file);

        uploadFuture.thenAccept(result -> System.out.println("Lesson upload completed successfully.")).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        return ResponseEntity.ok("Upload process initiated.");
    }

}
