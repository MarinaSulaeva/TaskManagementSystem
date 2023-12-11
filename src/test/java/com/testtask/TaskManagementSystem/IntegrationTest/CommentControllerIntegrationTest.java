package com.testtask.TaskManagementSystem.IntegrationTest;

import com.testtask.TaskManagementSystem.DTO.Priority;
import com.testtask.TaskManagementSystem.DTO.Role;
import com.testtask.TaskManagementSystem.DTO.Status;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.TaskService;
import com.testtask.TaskManagementSystem.service.impl.UserServiceImpl;
import net.minidev.json.JSONObject;
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

//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
public class CommentControllerIntegrationTest {
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Container
//    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
//            .withUsername("postgres")
//            .withPassword("73aberiv");
//
//    @DynamicPropertySource
//    static void postgresProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    private Task addToDb(String usernameAuthor, String usernameExecutor) {
//        commentRepository.deleteAll();
//        taskRepository.deleteAll();
//        usersRepository.deleteAll();
//        Users user = usersRepository.save(new Users(usernameAuthor,
//                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
//                Role.USER));
//        if (!usernameAuthor.equals(usernameExecutor)) {
//            Users userExecutor = usersRepository.save(new Users(
//                    usernameExecutor,
//                    "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
//                    Role.USER));
//            Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, userExecutor);
//            return taskRepository.save(task);
//        } else {
//            Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, user);
//            return taskRepository.save(task);
//        }
//    }
//
//    private Comment addComment() {
//        Task task = addToDb("user@gmail.com", "user1@gmail.com");
//        Comment comment = new Comment(1, usersRepository.findByUsername("user@gmail.com").orElseThrow(), task, LocalDateTime.now(), "comment text");
//        return commentRepository.save(comment);
//    }
//
//    private String getToken(String username, String password) throws Exception {
//        UserDetails userDetails = userService.loadUserByUsername(username);
//        return jwtTokenUtil.generateToken(userDetails);
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void createComment_status_isOk() throws Exception {
//        commentRepository.deleteAll();
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        Task task = addToDb("user@gmail.com", "user@gmail.com");
//        mockMvc.perform(post("/task/{id}/comment", task.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(newComment.toString()))
//                .andExpect(status().isOk());
//
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void createComment_status_isNotFound_Task() throws Exception {
//        Task task = addToDb("user@gmail.com", "user@gmail.com");
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        mockMvc.perform(post("/task/{id}/comment", 1)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newComment.toString()))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void changeComment_status_isOk() throws Exception {
//        Comment comment = addComment();
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newComment.toString()))
//                .andExpect(status().isOk());
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void changeComment_status_isNotFoundComment() throws Exception {
//        Comment comment = addComment();
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId()+1)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newComment.toString()))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void changeComment_status_isNotFoundTask() throws Exception {
//        Comment comment = addComment();
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId()+1, comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newComment.toString()))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void changeComment_status_403k() throws Exception {
//        Comment comment = addComment();
//        JSONObject newComment = new JSONObject();
//        newComment.put("text", "comment text");
//        mockMvc.perform(patch("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newComment.toString()))
//                .andExpect(status().isForbidden());
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void deleteComment_status_403() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password")))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void deleteComment_status_isOk() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void deleteComment_status_isNotFoundTask() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId()+1, comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void deleteComment_status_isNotFoundComment() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(delete("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId()+1)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void getComment_status_isOk() throws Exception {
//        Comment comment = addComment();
//        Integer id = comment.getTask().getId();
//        Integer commentId = comment.getId();
//        mockMvc.perform(get("/task/{id}/comment/{commentId}", id, commentId)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void getComment_status_isNotFoundTask() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(get("/task/{id}/comment/{commentId}", comment.getTask().getId()+1, comment.getId())
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void getComment_status_isNotFoundComment() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(get("/task/{id}/comment/{commentId}", comment.getTask().getId(), comment.getId()+1)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void getAllComment_status_isOk() throws Exception {
//        Comment comment = addComment();
//        mockMvc.perform(get("/task/{id}/comment/{page}",comment.getId(), 0)
//                        .queryParam("token", getToken("user@gmail.com", "password")))
//                                .andExpect(status().isOk());
//    }
}
