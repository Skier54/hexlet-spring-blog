package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.Post;
import hexlet.code.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    //Получение списка постов
    @Test
    void listPublished_returns200_andPage() throws Exception {
        mockMvc.perform(get("/api/posts")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    // Создание поста
    @Test
    void createPost_returns201_andBody() throws Exception {
        var body = """
            {
            "title": "Hello",
            "content": "Hello life!",
            "published": "true"
            }
        """;

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Hello"))
                .andExpect(jsonPath("$.content").value("Hello life!"))
                .andExpect(jsonPath("$.published").value(true));
    }

    // получения поста по id
    @Test
    void getPostById_returns200_andPost() throws Exception {
        var post = new Post();
        post.setTitle("Hello");
        post.setContent("Hello Hello life!");
        post.setPublished(true);
        postRepository.save(post);

        mockMvc.perform(get("/api/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.published").exists());
    }

    //обновления поста
    @Test
    void updatePost_returns200_andPost() throws Exception {
        var post = new Post();
        post.setTitle("Hello");
        post.setContent("Hello Hello life!");
        post.setPublished(true);
        postRepository.save(post);

        var updatedData = Map.of(
                "title", "Updated",
                "content", "contentName",
                "published", "true"
        );

        mockMvc.perform(put("/api/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("Updated"))
                .andExpect(jsonPath("$.content").value("contentName"))
                .andExpect(jsonPath("$.published").value("true"));
    }

    //удаления поста
    @Test
    void deletePost_returns204() throws Exception {
        var postId = 1L;

        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isNoContent());
    }
}
