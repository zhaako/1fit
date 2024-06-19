package cloud.client.cloudClient.model.dto;

import cloud.client.cloudClient.model.Coach;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewLessonDto {
    private String lessonName;
    private int lessonPrice;
}
