package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/comment/{commentId}")
    public void createComment(@PathVariable("id") Integer idTask, Authentication authentication, String text) {
        commentService.createComment(authentication.getName(), idTask, text);
    }

    @PatchMapping("{id}/comment/{commentId}")
    public CommentDTO changeComment(@PathVariable("id") Integer idTask, @PathVariable("commentId") Integer commentId, Authentication authentication, String newText) {
        return commentService.changeComment(authentication.getName(),commentId, newText);
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public void deleteComment(@PathVariable("id") Integer idTask, @PathVariable("commentId") Integer commentId, Authentication authentication) {
        commentService.deleteComment(authentication.getName(), commentId);
    }

    @GetMapping("/{id}/comment")
    public List<CommentDTO> getAllCommentsForTask(@PathVariable("id") Integer id) {
        return commentService.getAllCommentsForTask(id);
    }



}
