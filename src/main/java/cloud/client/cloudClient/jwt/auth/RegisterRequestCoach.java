package cloud.client.cloudClient.jwt.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestCoach {
    private String name;
    private String username;
    private String password;
    private String description;
    private String position;

}
