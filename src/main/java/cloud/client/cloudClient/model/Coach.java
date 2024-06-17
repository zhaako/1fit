package cloud.client.cloudClient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank
    @Column(name = "coachName")
    private String name;

    @NotBlank(message = "username couldn't be null")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "password couldn't be null")
    @Size(min = 8, message = "Password 8 symbol")
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private List<Lesson> lesson;
}
