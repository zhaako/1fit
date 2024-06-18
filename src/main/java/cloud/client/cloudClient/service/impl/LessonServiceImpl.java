package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.LessonCsv;
import cloud.client.cloudClient.repository.LessonRepository;
import cloud.client.cloudClient.service.LessonService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository repository;

    @Async
    @Override
    public CompletableFuture<Void> uploadLesson(MultipartFile file) throws IOException {
        Set<Lesson> lesson = parseCsv(file);
        repository.saveAll(lesson);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public List<Lesson> getAllLesson() {
        return repository.findAll();
    }

    @Override
    public Lesson addLesson(Lesson lesson) {
        return repository.save(lesson);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        repository.delete(lesson);
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
        return repository.save(lesson);
    }

    @Override
    public Lesson findLesson(Long id) {
        return repository.findLessonById(id);
    }

    private Set<Lesson> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<LessonCsv> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(LessonCsv.class);
            CsvToBean<LessonCsv> csvToBean =
                    new CsvToBeanBuilder<LessonCsv>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> Lesson.builder()
                            .lessonName(csvLine.getLessonName())
                            .lessonPrice(csvLine.getLessonPrice())
                            .build()
                    )
                    .collect(Collectors.toSet());
        }
    }
}
