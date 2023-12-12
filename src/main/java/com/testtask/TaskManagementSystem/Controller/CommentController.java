package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;
import com.testtask.TaskManagementSystem.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Класс-контроллер для работы с комментариями
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * метод для создания комментария
     */
    @PostMapping("/{id}/comment")
    @SecurityRequirement(name = "JWT")
    public Integer createComment(Authentication authentication,
                              @PathVariable("id") Integer idTask,
                              CreateOrUpdateComment comment) {
        return commentService.createComment(authentication.getName(), idTask, comment);
    }

    /**
     * Метод для изменения комментария
     */
    @PatchMapping("{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public CommentDTO changeComment(Authentication authentication,
                                    @PathVariable("id") Integer idTask,
                                    @PathVariable("commentId") Integer commentId,
                                    CreateOrUpdateComment comment) {
        return commentService.changeComment(authentication.getName(),commentId, comment);
    }

    /**
     * Метод для удаления комментария
     */
    @DeleteMapping("/{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public void deleteComment(Authentication authentication,
                              @PathVariable("id") Integer idTask,
                              @PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(authentication.getName(), commentId);
    }

    /**
     * Метод получения комментария
     */
    @GetMapping("/{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public CommentDTO getComment(@PathVariable("id") Integer idTask,
                                 @PathVariable("commentId") Integer commentId) {
        return commentService.getComment(commentId);
    }

    /**
     * Метод получения всех комментарий для задачи
     */
    @GetMapping("/{id}/comment/page")
    @SecurityRequirement(name = "JWT")
    public List<CommentDTO> getAllCommentsForTask(@PathVariable("id") Integer id,
                                                  @RequestParam("page") Integer page) {
        return commentService.getAllCommentsForTask(id, page);
    }





}
