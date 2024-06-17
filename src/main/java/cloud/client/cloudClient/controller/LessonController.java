package cloud.client.cloudClient.controller;

import cloud.client.cloudClient.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
public class LessonController {
    @Autowired
    private LessonService lessonService;


//    http://localhost:1000/swagger-ui/index.html#/
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadLessons(@RequestParam("file") MultipartFile file) throws IOException {
        CompletableFuture<Void> uploadFuture = lessonService.uploadLesson(file);

        uploadFuture.thenAccept(result -> {
            System.out.println("Lesson upload completed successfully.");
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        return ResponseEntity.ok("Upload process initiated.");
    }

}
