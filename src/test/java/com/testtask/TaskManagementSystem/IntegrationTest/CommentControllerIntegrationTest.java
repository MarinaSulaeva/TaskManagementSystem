package com.testtask.TaskManagementSystem.IntegrationTest;


import com.testtask.TaskManagementSystem.DTO.Priority;
import com.testtask.TaskManagementSystem.DTO.Role;
import com.testtask.TaskManagementSystem.DTO.Status;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.UserService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CommentControllerIntegrationTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private CommentRepository commentRepository;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void clearDb() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        usersRepository.deleteAll();
    }

    private Task addToDb(String usernameExecutor) {
        User user = usersRepository.save(new User("user@gmail.com",
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER));
        if (!"user@gmail.com".equals(usernameExecutor)) {
            User userExecutor = usersRepository.save(new User(usernameExecutor,
                    "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                    Role.USER));
            Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, userExecutor);
            return taskRepository.save(task);
        } else {
            Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, user);
            return taskRepository.save(task);
        }
    }

    private Comment addComment() {
        Task task = addToDb("user1@gmail.com");
        Comment comment = new Comment(usersRepository.findByUsername("user@gmail.com").orElseThrow(), task, LocalDateTime.now(), "comment text");
        return commentRepository.save(comment);
    }

    private String getToken(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.createAccessToken(userDetails);
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void createComment_status_isOk() throws Exception {
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        Task task = addToDb("user1@gmail.com");
        mockMvc.perform(post("/task/{id}/comment", task.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void createComment_status_isNotFound_Task() throws Exception {
        addToDb("user@gmail.com");
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        mockMvc.perform(post("/task/{id}/comment", 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void changeComment_status_isOk() throws Exception {
        Comment comment = addComment();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void changeComment_status_isNotFoundComment() throws Exception {
        Comment comment = addComment();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId() + 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void changeComment_status_isNotFoundTask() throws Exception {
        Comment comment = addComment();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId() + 1, comment.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1@gmail.com", roles = "USER")
    public void changeComment_status_403k() throws Exception {
        Comment comment = addComment();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "comment text");
        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1@gmail.com", roles = "USER")
    public void deleteComment_status_403() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com")))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void deleteComment_status_isOk() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void deleteComment_status_isNotFoundTask() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId() + 1, comment.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void deleteComment_status_isNotFoundComment() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId() + 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void getComment_status_isOk() throws Exception {
        Comment comment = addComment();
        Integer id = comment.getTask().getId();
        Integer commentId = comment.getId();
        mockMvc.perform(get("/task/{id}/comment/{commentId}", id, commentId)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void getComment_status_isNotFoundTask() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(get("/task/{id}/comment/{commentId}", comment.getTask().getId() + 1, comment.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void getComment_status_isNotFoundComment() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(get("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId() + 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    public void getAllComment_status_isOk() throws Exception {
        Comment comment = addComment();
        mockMvc.perform(get("/task/{id}/comment/page", comment.getTask().getId()).param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user@gmail.com")))
                .andExpect(status().isOk());
    }
}
