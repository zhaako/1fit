package cloud.client.cloudClient.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LessonCsv {

    @CsvBindByName(column = "lessonName")
    private String lessonName;
    @CsvBindByName(column = "lessonPrice")
    private int lessonPrice;
}
