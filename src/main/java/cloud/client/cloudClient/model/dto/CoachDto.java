package cloud.client.cloudClient.model.dto;


import cloud.client.cloudClient.model.Lesson;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto extends UserDto{
    private String position;
    private int description;
    private List<NewLessonDto> lessons;
}
