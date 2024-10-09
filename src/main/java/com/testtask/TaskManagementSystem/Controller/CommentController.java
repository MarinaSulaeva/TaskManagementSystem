package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;
import com.testtask.TaskManagementSystem.service.CommentService;
import com.testtask.TaskManagementSystem.swagger.CommentControllerInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс-контроллер для работы с комментариями
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class CommentController implements CommentControllerInterface {

    private final CommentService commentService;

    /**
     * метод для создания комментария
     */
    @PostMapping("/{id}/comment")
    public ResponseEntity<Integer> createComment(Authentication authentication,
                                                @PathVariable("id") Integer idTask,
                                                @RequestBody CreateOrUpdateComment comment) {
        Integer id = commentService.createComment(authentication.getName(), idTask, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /**
     * Метод для изменения комментария
     */
    @PatchMapping("{id}/comment/{commentId}")
    public ResponseEntity<CommentDTO> changeComment(Authentication authentication,
                                    @PathVariable("id") Integer idTask,
                                    @PathVariable("commentId") Integer commentId,
                                    @RequestBody CreateOrUpdateComment comment) {
        CommentDTO commentDTO = commentService.changeComment(authentication.getName(), commentId, comment, idTask);
        return ResponseEntity.ok(commentDTO);
    }

    /**
     * Метод для удаления комментария
     */
    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(Authentication authentication,
                              @PathVariable("id") Integer idTask,
                              @PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(authentication.getName(), commentId, idTask);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Метод получения комментария
     */
    @GetMapping("/{id}/comment/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") Integer idTask,
                                 @PathVariable("commentId") Integer commentId) {
        return ResponseEntity.ok(commentService.getComment(commentId, idTask));
    }

    /**
     * Метод получения всех комментарий для задачи
     */
    @GetMapping("/{id}/comment/page")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForTask(@PathVariable("id") Integer id,
                                                  @RequestParam("page") Integer page) {
        return ResponseEntity.ok(commentService.getAllCommentsForTask(id, page));
    }


}
