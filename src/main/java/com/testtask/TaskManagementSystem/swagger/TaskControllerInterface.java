package com.testtask.TaskManagementSystem.swagger;

import com.testtask.TaskManagementSystem.DTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "tasks", description = "Создание, изменение, удаление и просмотр задач пользователей")
public interface TaskControllerInterface {

    @Operation(summary = "Создание задачи (необходима аутентификация)",
            description = "В тело необходимо передать  title (заголовок), descpription " +
                    "(описание задачи), priority (приоритет: HIGH, MEDIUM, LOW), executorUsername " +
                    "(логин пользователя-исполнителя задачи)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешное создание задачи"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<Integer> createTask(Authentication authentication,
                                             @RequestBody @Valid TaskForCreate taskForCreate);

    @Operation(summary = "Редактирование задачи (необходима аутентификация)",
            description = "В тело необходимо передать  id задачи, title (заголовок), descpription " +
                    "(описание задачи) и priority (приоритет: HIGH, MEDIUM, LOW)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное изменение задачи"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Данная задача принадлежит другому пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<TaskDTO> editTask(Authentication authentication,
                            @RequestBody @Valid TaskForChange taskForChange);

    @Operation(summary = "Удаление задачи (необходима аутентификация)",
            description = "В тело необходимо передать  id задачи, которую необходимо удалить")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача удалена"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Данная задача принадлежит другому пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<?> deleteTask(Authentication authentication,
                           @PathVariable Integer id);


    @Operation(summary = "Получение задачи на id (необходима аутентификация)",
            description = "В тело необходимо передать  id задачи, которую необходимо найти")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer id);


    @Operation(summary = "Изменение статуса задачи (необходима аутентификация)",
            description = "В тело необходимо передать  id задачи, которую необходимо найти " +
                    "и новый статус. Изменить статус задачи может только автор или исполнитель задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус задачи изменился"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Данная задача принадлежит другому пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<?> changeStatusOfTask(Authentication authentication,
                                   @PathVariable Integer id,
                                   @RequestBody ChangeStatus newStatus);

    @Operation(summary = "Добавление исполнителя задачи (необходима аутентификация)",
            description = "В тело необходимо передать  id задачи, в которую необходимо добавить " +
                    " исполнителя и самого исполнителя.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Исполнитель задачи добавлен"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Данная задача принадлежит другому пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<UsersDTO> addExecutorForTask(Authentication authentication,
                                       @PathVariable Integer id,
                                       @RequestBody @Valid UsersDTO usersDTO);

    @Operation(summary = "Получение всех задач другого пользователя (необходима аутентификация)",
            description = "В тело необходимо передать  id пользователя, которого необходимо " +
                    " получить задачи и номер страницы.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задачи найдены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<TaskDTO>> getAllTaskForOtherUser(@RequestBody @Valid UsersDTO usersDTO,
                                                @RequestParam("page") Integer page);


    @Operation(summary = "Получение всех задач (необходима аутентификация)",
            description = "В тело необходимо передать номер страницы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задачи найдены")
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<TaskDTO>> getAllTask(@RequestParam("page") Integer page);
}
