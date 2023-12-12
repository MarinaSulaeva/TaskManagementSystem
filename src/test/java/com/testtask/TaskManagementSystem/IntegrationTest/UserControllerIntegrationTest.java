package com.testtask.TaskManagementSystem.IntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
public class UserControllerIntegrationTest {
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
//    private void addToDb() {
//        commentRepository.deleteAll();
//        taskRepository.deleteAll();
//        usersRepository.deleteAll();
//        Users user = usersRepository.save(new Users("user@gmail.com",
//                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
//                Role.USER));
//        Users userExecutor = usersRepository.save(new Users("user1@gamil.com",
//                "$2a$10$DIbqqLodN24iFcXG2YNqvOyz4LcBKhFPF9viA3RzDea09YBHCBlse",
//                Role.USER));
//        Task task = new Task("task", "task description", Status.CREATED, Priority.HIGH, user, userExecutor);
//        taskRepository.save(task);
//        commentRepository.save(new Comment(usersRepository.findByUsername("user@gmail.com").orElseThrow(), task, LocalDateTime.now(), "comment text"));
//    }
//
//
//    private String getToken(String username, String password) throws Exception {
//        JSONObject login = new JSONObject();
//        login.put("username", "user@gmail.com");
//        login.put("password", "password");
//        MvcResult result = mockMvc.perform(post("/auth")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(login.toString()))
//                .andExpect(status().isOk()).andReturn();
//        return result.toString();
////        UserDetails userDetails = userService.loadUserByUsername(username);
////        return jwtTokenUtil.generateToken(userDetails);
//    }

//    @Test
//    @WithMockUser(username = "user@gmail.com", password = "password", roles = "USER")
//    public void getAllMyComment_status_isOk() throws Exception {
//        addToDb();
//        mockMvc.perform(get("/user/myComment/{page}", 0)
//                        .header("Authorization", "Bearer " + getToken("user@gmail.com", "password")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user1@gmail.com", password = "password", roles = "USER")
//    public void getMyTaskForExecution_status_isOk() throws Exception {
//        addToDb();
//        mockMvc.perform(get("/user/my_task_for_execution{page}", 0)
//                        .header("Authorization", "Bearer " + getToken("user1@gmail.com", "password")))
//                .andExpect(status().isOk());
//    }

}
