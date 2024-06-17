package cloud.client.cloudClient.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "lessonName")
    private String lessonName;

    @Column(name = "lessonPrice")
    private int lessonPrice;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;
}
