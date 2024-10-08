package cloud.client.cloudClient.model.dto;

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
