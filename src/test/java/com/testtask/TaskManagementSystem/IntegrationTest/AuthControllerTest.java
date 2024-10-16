package com.testtask.TaskManagementSystem.IntegrationTest;

import com.testtask.TaskManagementSystem.DTO.JwtRequest;
import com.testtask.TaskManagementSystem.DTO.JwtResponse;
import com.testtask.TaskManagementSystem.DTO.Role;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.repository.RefreshTokenRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.AuthService;
import com.testtask.TaskManagementSystem.service.UserService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

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

    private void addToDb() {
        usersRepository.deleteAll();
        User user = new User(1,
                "user@gmail.com",
                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
                Role.USER);
        usersRepository.save(user);
    }

    @BeforeEach
    public void clearDb() {
        usersRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }

    private String getRefreshToken(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.createRefreshToken(userDetails);
    }

    @Test
    public void getToken_status_isOk() throws Exception {
        addToDb();
        JSONObject login = new JSONObject();
        login.put("username", "user@gmail.com");
        login.put("password", "password");
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getToken_status_isUnavtorized() throws Exception {
        addToDb();
        JSONObject login = new JSONObject();
        login.put("username", "user@gmail.com");
        login.put("password", "password1");
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getToken_status_isNotValid() throws Exception {
        addToDb();
        JSONObject login = new JSONObject();
        login.put("username", "user@gmail.commmmmmmmmmmmmmmmmmmmm");
        login.put("password", "password11111111111111111111");
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_status_is201() throws Exception {
        JSONObject register = new JSONObject();
        register.put("username", "user@gmail.com");
        register.put("password", "password");
        register.put("role", Role.USER);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(register.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void register_status_is400() throws Exception {
        addToDb();
        JSONObject register = new JSONObject();
        register.put("username", "user@gmail.com");
        register.put("password", "password");
        register.put("role", Role.USER);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(register.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_status_isNotValid() throws Exception {
        JSONObject register = new JSONObject();
        register.put("username", "user@gmail.commmmmmmmmmmmmmmmmmmmmmmmm");
        register.put("password", "passworddddddddddddddddddddddddd");
        register.put("role", Role.USER);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(register.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRefreshToken_isOk() throws Exception {
        addToDb();
        JwtResponse jwtResponse = authService.createToken(new JwtRequest("user@gmail.com", "password"));
        JSONObject jwtRefreshToken = new JSONObject();
        jwtRefreshToken.put("refreshToken", jwtResponse.getRefreshToken());

        mockMvc.perform(post("/auth/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jwtRefreshToken.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getRefreshToken_401() throws Exception {
        addToDb();
        JwtResponse jwtResponse = authService.createToken(new JwtRequest("user@gmail.com", "password"));
        JSONObject jwtRefreshToken = new JSONObject();
        jwtRefreshToken.put("refreshToken", jwtResponse.getAccessToken());

        mockMvc.perform(post("/auth/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jwtRefreshToken.toString()))
                .andExpect(status().isUnauthorized());
    }


}
