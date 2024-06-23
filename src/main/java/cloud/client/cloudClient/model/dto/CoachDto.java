package cloud.client.cloudClient.model.dto;


import cloud.client.cloudClient.model.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto extends UserDto{
    private String position;
    private String description;
    private List<NewLessonDto> lessons;
    private Role role;
}
