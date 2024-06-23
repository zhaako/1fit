package cloud.client.cloudClient.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("Coach")
@AllArgsConstructor
@NoArgsConstructor
public class Coach extends User {
    private String position;
    private String description;
}
