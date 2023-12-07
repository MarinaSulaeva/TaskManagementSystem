package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository
    @Override
    public void createComment(String username, Integer idTask, String text) {
        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        comment.setTask(taskRepository.findById(idTask));
    }

    @Override
    public CommentDTO changeComment(String username, Integer idTask, Integer idComment) {
        return null;
    }

    @Override
    public void deleteComment(String username, Integer idTask, Integer idComment) {

    }

    @Override
    public CommentDTO getComment(String username, Integer idTask, Integer idComment) {
        return null;
    }

    @Override
    public List<CommentDTO> getAllCommentsForTask(String username, Integer idTask) {
        return null;
    }
}
