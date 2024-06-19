package cloud.client.cloudClient.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Getter
@Setter
@DiscriminatorValue("Coach")
public class Coach extends User {

    private String position;
    private int description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private List<Lesson> lessons;
}

