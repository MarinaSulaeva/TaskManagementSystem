package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.service.TaskService;
import com.testtask.TaskManagementSystem.swagger.TaskControllerInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

/**
 * Класс-контроллер для работы с с задачами
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController implements TaskControllerInterface {

    private final TaskService taskService;

    /**
     * Метод для создания задачи
     */
    @PostMapping
    public ResponseEntity<Integer> createTask(Authentication authentication,
                                             @RequestBody @Valid TaskForCreate taskForCreate) {
        Integer id = taskService.createTask(authentication.getName(), taskForCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /**
     * Метод для изменения задачи
     */
    @PutMapping
    public ResponseEntity<TaskDTO> editTask(Authentication authentication,
                            @RequestBody @Valid TaskForChange taskForChange) {
        return ResponseEntity.ok(taskService.editTask(authentication.getName(), taskForChange));
    }

    /**
     * Метод для удаления задачи
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(Authentication authentication,
                           @PathVariable Integer id) {
        taskService.deleteTask(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Метод для получения задачи по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    /**
     * Метод для изменения статуса задачи
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatusOfTask(Authentication authentication,
                                   @PathVariable Integer id,
                                   @RequestBody ChangeStatus newStatus) {
        taskService.changeStatusOfTask(authentication.getName(), id, newStatus.getStatus());
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для добавления/изменения исполнителя задачи
     */
    @PatchMapping("/{id}/executor")
    public ResponseEntity<UsersDTO> addExecutorForTask(Authentication authentication,
                                       @PathVariable Integer id,
                                       @RequestBody @Valid UsersDTO usersDTO) {
        return ResponseEntity.ok(taskService.addExecutorForTask(authentication.getName(), id, usersDTO.getEmail()));
    }

    /**
     * Метод для получения задач другого пользователя
     */
    @GetMapping("/other/page")
    public ResponseEntity<List<TaskDTO>> getAllTaskForOtherUser(@RequestBody @Valid UsersDTO usersDTO,
                                                @RequestParam("page") Integer page) {
        List<TaskDTO> taskDTOS = taskService.getAllTaskToOtherAuthors(usersDTO.getEmail(), page);
        return ResponseEntity.ok(taskDTOS);
    }

    /**
     * Метод получения всех задач
     */
    @GetMapping("/all/page")
    public ResponseEntity<List<TaskDTO>> getAllTask(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(taskService.getAllTask(page));
    }

}
