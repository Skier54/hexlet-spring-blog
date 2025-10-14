package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Создание пользователя
    @Test
    void createUser_returns201_andBody() throws Exception {
        var body = """
            {
            "firstName": "John",
            "lastName": "Doe",
            "email": "john@example.com"
            }
        """;

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // Получение списка пользователей
    @Test
    void getUsersList_returns200_andList() throws Exception {
        mockMvc.perform(get("/api/users")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").exists());
    }

    // получения пользователя по id
    @Test
    void getUserById_returns200_andUser() throws Exception {
        var userId = 1L;
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists());
    }

    //обновления пользователя
    @Test
    void updateUser_returns200_andUser() throws Exception {
        var userId = 1L;

        var updatedData = Map.of(
                "firstName", "Updated",
                "lastName", "Name",
                "email", "newemail@example.com"
        );

        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"))
                .andExpect(jsonPath("$.email").value("newemail@example.com"));
    }

    //удаления пользователя
    @Test
    void deleteUser_returns204() throws Exception {
        var userId = 1L;

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }
}
