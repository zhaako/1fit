package cloud.client.cloudClient.service;

import cloud.client.cloudClient.model.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LessonService {
    CompletableFuture<Void> uploadLesson(MultipartFile file) throws IOException;
    List<Lesson> getAllLesson();
    Lesson addLesson(Lesson lesson);
    void deleteLesson(Lesson lesson);
    Lesson saveLesson(Lesson lesson);

    Lesson findLesson(Long id);
}
