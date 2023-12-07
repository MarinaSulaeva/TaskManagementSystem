package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import com.testtask.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final TaskService taskService;
    private final CommentService commentService;

    @GetMapping("/myComments")
    public List<CommentDTO> getAllMyComments(Authentication authentication) {
        return commentService.getAllCommentsForAuthor(authentication.getName());
    }
}
