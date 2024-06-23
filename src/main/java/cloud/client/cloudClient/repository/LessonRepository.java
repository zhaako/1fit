package cloud.client.cloudClient.repository;

import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.LessonCsv;
import cloud.client.cloudClient.model.dto.NewLessonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findLessonById(Long id);

    @Query("SELECT new cloud.client.cloudClient.model.dto.NewLessonDto(l.lessonName, l.lessonPrice) " +
            "FROM Lesson l WHERE l.coach.id = :coachId")
    List<NewLessonDto> findAllByCoachId(@Param("coachId") Long id);
}
