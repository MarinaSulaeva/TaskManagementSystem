package com.testtask.TaskManagementSystem.swagger;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;
import com.testtask.TaskManagementSystem.DTO.ErrorResponse;
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

import java.util.List;

@Tag(name = "comments", description = "Создание, изменение, удаление и просмотр комментариев к задачам")
public interface CommentControllerInterface {
    @Operation(summary = "Создание комментария (необходима аутентификация)",
            description = "В тело необходимо передать id задачи, а также  text (текст комментария).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешное создание комментария"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<Integer> createComment(Authentication authentication,
                                          @PathVariable("id") Integer idTask,
                                          @RequestBody CreateOrUpdateComment comment);

    @Operation(summary = "Изменение комментария (необходима аутентификация)",
            description = "В тело необходимо передать id задачи, id комментария, " +
                    "а также  text (новый текст комментария). Можно изменить только свой комментарий.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное изменение комментария"),
            @ApiResponse(responseCode = "404", description = "Задача или комментарий не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Комментарий не принадлежит пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<CommentDTO> changeComment(Authentication authentication,
                                    @PathVariable("id") Integer idTask,
                                    @PathVariable("commentId") Integer commentId,
                                    @RequestBody CreateOrUpdateComment comment);

    @Operation(summary = "Удаление комментария (необходима аутентификация)",
            description = "В тело необходимо передать id задачи и id комментария. " +
                    "Удалить можно только свой комментарий.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Успешное удаление комментария"),
            @ApiResponse(responseCode = "404", description = "Задача или комментарий не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Комментарий не принадлежит пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<?> deleteComment(Authentication authentication,
                              @PathVariable("id") Integer idTask,
                              @PathVariable("commentId") Integer commentId);

    @Operation(summary = "Получение комментария по id(необходима аутентификация)",
            description = "В тело необходимо передать id задачи и id комментария. ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение комментария"),
            @ApiResponse(responseCode = "404", description = "Задача или комментарий не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<CommentDTO> getComment(@PathVariable("id") Integer idTask,
                                 @PathVariable("commentId") Integer commentId);

    @Operation(summary = "Получение всех комментариев задачи(необходима аутентификация)",
            description = "В тело необходимо передать id задачи.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение комментариев"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "JWT")
    ResponseEntity<List<CommentDTO>> getAllCommentsForTask(@PathVariable("id") Integer id,
                                                  @RequestParam("page") Integer page);
}
