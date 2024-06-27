package cloud.client.cloudClient;

import cloud.client.cloudClient.model.Lesson;
import cloud.client.cloudClient.model.dto.NewLessonDto;
import cloud.client.cloudClient.service.LessonService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class CloudClientApplicationTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private LessonService lessonService;
	@Container
	private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
		registry.add("spring.jpa.generate-ddl", () -> true);
	}

	@Test
	@SneakyThrows
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void getAllLesson() {
		var result = mvc.perform(MockMvcRequestBuilders.get("/api/lesson"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		var lessons = lessonService.getAllLesson();
		assertThat(lessons).isNotNull();
		var objectMapper = new ObjectMapper();
		var expectedJson = objectMapper.writeValueAsString(lessons);
		assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
	}
	@Test
	@SneakyThrows
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void updateLesson() {
		Lesson lesson = new Lesson();
		lesson.setLessonName("Box");
		lesson.setLessonPrice(20000);
		lesson = lessonService.saveLesson(lesson);

		NewLessonDto newLessonDto = new NewLessonDto();
		newLessonDto.setLessonName("Judo");
		newLessonDto.setLessonPrice(15000);

		var objectMapper = new ObjectMapper();
		var lessonJson = objectMapper.writeValueAsString(newLessonDto);

		mvc.perform(put("/api/lesson/" + lesson.getId())
						.contentType("application/json")
						.content(lessonJson))
				.andExpect(status().isOk())
				.andExpect(content().string("Lesson updated successfully"));

		Lesson updatedLesson = lessonService.findLesson(lesson.getId());
		assertThat(updatedLesson.getLessonName()).isEqualTo("Judo");
		assertThat(updatedLesson.getLessonPrice()).isEqualTo(15000);
	}

	@Test
	@SneakyThrows
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void saveLesson(){
		Lesson lesson = new Lesson();
		lesson.setLessonName("Box");
		lesson.setLessonPrice(20000);
		lessonService.saveLesson(lesson);
		var objectMapper = new ObjectMapper();
		var lessonJson = objectMapper.writeValueAsString(lesson);
		mvc.perform(post("/api/lesson/add")
						.contentType("application/json")
						.content(lessonJson))
				.andExpect(status().isOk())
				.andExpect(content().string("Lesson added successfully"));
		lessonService.findLesson(lesson.getId());
	}
	@Test
	@SneakyThrows
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void deleteLesson(){
		Lesson lesson = new Lesson();
		lesson.setLessonName("Box");
		lesson.setLessonPrice(20000);
		lesson = lessonService.saveLesson(lesson); // persist the lesson

		var objectMapper = new ObjectMapper();
		var lessonJson = objectMapper.writeValueAsString(lesson);
		mvc.perform(delete("/api/lesson/" + lesson.getId())
						.contentType("application/json")
						.content(lessonJson))
				.andExpect(status().isOk())
				.andExpect(content().string("Lesson deleted successfully"));

	}
}
