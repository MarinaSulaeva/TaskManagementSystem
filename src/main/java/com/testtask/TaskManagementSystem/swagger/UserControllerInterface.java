package com.testtask.TaskManagementSystem.swagger;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "user", description = "Просмотр своих задач, комментариев и задач для выполнения")
public interface UserControllerInterface {

    @Operation(summary = "Получение всех задач пользователя(необходима аутентификация)",
            description = "В тело необходимо передать номер страницы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задачи найдены")
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<TaskDTO>> getAllMyTasks(Authentication authentication,
                                                @RequestParam("page") Integer page);

    @Operation(summary = "Получение всех задач на исполнение(необходима аутентификация)",
            description = "В тело необходимо передать номер страницы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задачи найдены")
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<TaskDTO>> getMyTasksForExecution(Authentication authentication,
                                                @RequestParam("page") Integer page);

    @Operation(summary = "Получение всех комментариев пользователя (необходима аутентификация)",
            description = "В тело необходимо передать номер страницы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задачи найдены")
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<CommentDTO>> getAllMyComments(Authentication authentication,
                                             @RequestParam("page") Integer page);
}
