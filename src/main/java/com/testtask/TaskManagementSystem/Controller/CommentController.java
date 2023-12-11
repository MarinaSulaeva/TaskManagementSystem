package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/comment/")
    @SecurityRequirement(name = "JWT")
    public void createComment(@PathVariable("id") Integer idTask, Authentication authentication, String text) {
        commentService.createComment(authentication.getName(), idTask, text);
    }

    @PatchMapping("{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public CommentDTO changeComment(@PathVariable("id") Integer idTask, @PathVariable("commentId") Integer commentId, Authentication authentication, String newText) {
        return commentService.changeComment(authentication.getName(),commentId, newText);
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public void deleteComment(@PathVariable("id") Integer idTask, @PathVariable("commentId") Integer commentId, Authentication authentication) {
        commentService.deleteComment(authentication.getName(), commentId);
    }

    @GetMapping("/{id}/comment/{commentId}")
    @SecurityRequirement(name = "JWT")
    public CommentDTO getComment(@PathVariable("id") Integer idTask, @PathVariable("commentId") Integer commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping("/{id}/comment/{page}")
    @SecurityRequirement(name = "JWT")
    public List<CommentDTO> getAllCommentsForTask(@PathVariable("id") Integer id, @PathVariable("page") Integer page) {
        return commentService.getAllCommentsForTask(id, page);
    }





}
