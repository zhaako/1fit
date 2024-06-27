package cloud.client.cloudClient;

import cloud.client.cloudClient.service.CoachService;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class CoachControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CoachService coachService;

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void getAllCoach(){
        var result = mvc.perform(MockMvcRequestBuilders.get("/api/coach"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var coaches = coachService.getAllNeedCoach();
        Assertions.assertThat(coaches).isNotNull();
        var objectMapper = new ObjectMapper();
        var expectedJson = objectMapper.writeValueAsString(coaches);
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void addCoach(){

    }
}
