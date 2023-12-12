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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {
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
            .withPassword("73aberiv");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private void addToDb() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        usersRepository.deleteAll();
        User user = usersRepository.save(new User("user@gmail.com",
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER));
        User userExecutor = usersRepository.save(new User("user1@gmail.com",
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER));
        Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, userExecutor);
        taskRepository.save(task);
        commentRepository.save(new Comment(usersRepository.findByUsername("user@gmail.com").orElseThrow(), task, LocalDateTime.now(), "comment text"));
    }


    private String getToken(String username, String password) throws Exception {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Test
    @WithMockUser(username = "user1@gmail.com", password = "password", roles = "USER")
    public void getMyTaskForExecution_status_isOk() throws Exception {
        addToDb();
        mockMvc.perform(get("/user/my_task_for_execution/page").param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllMyTask() throws Exception {
        addToDb();
        mockMvc.perform(get("/user/my_task/page").param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isOk());
    }

}
