package cloud.client.cloudClient.repository;

import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.LessonCsv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findLessonById(Long id);
}
