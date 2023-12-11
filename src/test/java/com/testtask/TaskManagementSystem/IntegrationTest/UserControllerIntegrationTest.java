package com.testtask.TaskManagementSystem.IntegrationTest;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private UserServiceImpl userService;

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

    private Task addToDb(String usernameAuthor, String usernameExecutor) {
        taskRepository.deleteAll();
        usersRepository.deleteAll();
        Users user = usersRepository.save(new Users(1,
                usernameAuthor,
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER));
        if (!usernameAuthor.equals(usernameExecutor)) {
            Users userExecutor = usersRepository.save(new Users(1,
                    usernameExecutor,
                    "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                    Role.USER));
            Task task = new Task(1, "task", "task description", Status.CREATED, Priority.HIGH, user, userExecutor);
            return taskRepository.save(task);
        } else {
            Task task = new Task(1, "task", "task description", Status.CREATED, Priority.HIGH, user, user);
            return taskRepository.save(task);
        }
    }

    private Comment addComment() {
        Task task = addToDb("user@gmail.com", "user1@gmail.com");
        Comment comment = new Comment(1, usersRepository.findByUsername("user@gmail.com").orElseThrow(), task, LocalDateTime.now(), "comment text");
        return commentRepository.save(comment);
    }

    private String getToken(String username, String password) throws Exception {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllMyComment_status_isOk() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(get("/user/myComment/{page}", 0)
                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
    public void getAllMyComment_status_is403() throws Exception {
        Task task = addToDb("user@gmail.com", "user@gmail.com");
        mockMvc.perform(get("/user/myComment/{page}", 0)
                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password")))
                .andExpect(status().isForbidden());
    }

//    @GetMapping("/myComments/page")
//    @SecurityRequirement(name = "JWT")
//    public List<CommentDTO> getAllMyComments(Authentication authentication, @RequestParam("page") Integer page) {
//        return commentService.getAllCommentsForAuthor(authentication.getName(), page);
//    }
//
//    @GetMapping("/my_task_for_execution/page")
//    @SecurityRequirement(name = "JWT")
//    public List<TaskDTO> getMyTasksForExecution(Authentication authentication, @RequestParam("page") Integer page) {
//        return taskService.getAllTaskForExecutor(authentication.getName(), page);
//    }


}
