package com.testtask.TaskManagementSystem.IntegrationTest;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class TaskControllerIntegrationTest {
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


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("73aberiv");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private Task addToDb(String usernameAuthor, String usernameExecutor) {
        taskRepository.deleteAll();
        usersRepository.deleteAll();
        User user = usersRepository.save(new User(usernameAuthor,
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER));
        if (!usernameAuthor.equals(usernameExecutor)) {
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

    private String getToken(String username, String password) throws Exception {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }


    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void createTask_status_isOk() throws Exception {
        addToDb("user@gmail.com", "user@gmail.com");
        JSONObject taskForCreate = new JSONObject();
        taskForCreate.put("title", "task title");
        taskForCreate.put("description", "task description");
        taskForCreate.put("priority", "LOW");
        taskForCreate.put("executorName", "user@gmail.com");
        mockMvc.perform(post("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForCreate.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void createTask_status_isNotValid() throws Exception {
        addToDb("user@gmail.com", "user@gmail.com");
        JSONObject taskForCreate = new JSONObject();
        taskForCreate.put("title", "task titleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        taskForCreate.put("description", "task descriptionnnnnnnnnnnnnn");
        taskForCreate.put("priority", "LOW");
        taskForCreate.put("executorName", "user@gmail.com");
        mockMvc.perform(post("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForCreate.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void editTask_status_isOk() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        JSONObject taskForChange = new JSONObject();
        taskForChange.put("id", task.getId());
        taskForChange.put("title", "task title");
        taskForChange.put("description", "task description");
        taskForChange.put("priority", "MEDIUM");
        mockMvc.perform(put("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForChange.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void editTask_status_403() throws Exception {
        Task task = addToDb("user1@gmail.com", "user@gmail.com");
        JSONObject taskForChange = new JSONObject();
        taskForChange.put("id", task.getId());
        taskForChange.put("title", "task title");
        taskForChange.put("description", "task description");
        taskForChange.put("priority", "MEDIUM");
        mockMvc.perform(put("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForChange.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void editTask_status_NotFoundTask() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        JSONObject taskForChange = new JSONObject();
        taskForChange.put("id", 1);
        taskForChange.put("title", "task title");
        taskForChange.put("description", "task description");
        taskForChange.put("priority", "MEDIUM");
        mockMvc.perform(put("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForChange.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void editTask_status_notValid() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        JSONObject taskForChange = new JSONObject();
        taskForChange.put("id", task.getId());
        taskForChange.put("title", "task title1111111111111111111111111");
        taskForChange.put("description", "task description111111111111111111111111");
        taskForChange.put("priority", "MEDIUM");
        mockMvc.perform(put("/task")
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskForChange.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void deleteTask_status_isOk() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(delete("/task/{id}", task.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void deleteTask_status_isNotFound_Task() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(delete("/task/{id}", 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1@gmail.com", password = "password", roles = "USER")
    public void deleteTask_status_403() throws Exception {
        Task task = addToDb("user@gmail.com", "user1@gmail.com");
        mockMvc.perform(delete("/task/{id}", task.getId())
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password")))
                .andExpect(status().isForbidden());
    }



    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getTaskById_isOk() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(get("/task/{id}", task.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getTaskById_isNotFound() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(get("/task/{id}", 1)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void changeStatus_isOk() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        JSONObject status = new JSONObject();
        status.put("status", "STARTED");
        mockMvc.perform(patch("/task/{id}/status",task.getId())
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(status.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1@gmail.com", password = "password", roles = "USER")
    public void addExecutor_403() throws Exception {
        Task task = addToDb("user@gmail.com", "user1@gmail.com");
        JSONObject executor = new JSONObject();
        executor.put("email", "user@gmail.com");
        mockMvc.perform(patch("/task/{id}/executor",task.getId())
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(executor.toString()))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllTaskForOtherUser_isOk() throws Exception {
        addToDb("user@gmail.com", "user1@gmail.com");
        JSONObject otherUser = new JSONObject();
        otherUser.put("email", "user1@gmail.com");
        mockMvc.perform(get("/task/other/page").param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otherUser.toString()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllTaskForOtherUser_isNotFound() throws Exception {
        addToDb("user@gmail.com", "user1@gmail.com");
        JSONObject otherUser = new JSONObject();
        otherUser.put("email", "user2@gmail.com");
        mockMvc.perform(get("/task/other/page").param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otherUser.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllTask_isOk() throws Exception {
        addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(get("/task/all/page").param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isOk());
    }

}
