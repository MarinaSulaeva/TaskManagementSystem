package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Users;
import com.testtask.TaskManagementSystem.exceptions.CommentNotFoundException;
import com.testtask.TaskManagementSystem.exceptions.TaskNotFoundException;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    private Comment checkAuthor(String username, Integer idComment) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(() -> new CommentNotFoundException("комментарий не найден"));
        if (!comment.getAuthor().equals(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден")))){
            throw new CommentNotFoundException("комментарий принадежит другому пользователю");
        }
        return comment;
    }
    @Override
    public void createComment(String username, Integer idTask, String text) {
        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        comment.setTask(taskRepository.findById(idTask).orElseThrow(() -> new TaskNotFoundException("задача не найдена")));
        comment.setText(text);
        commentRepository.save(comment);
    }

    @Override
    public CommentDTO changeComment(String username, Integer idComment, String newText) {
        Comment comment = checkAuthor(username, idComment);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setText(newText);
        return CommentDTO.fromComment(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(String username, Integer idComment) {
        commentRepository.delete(checkAuthor(username, idComment));
    }

    @Override
    public CommentDTO getComment(Integer idComment) {
        return CommentDTO.fromComment(commentRepository.findById(idComment).orElseThrow(() -> new CommentNotFoundException("комментарий не найден")));
    }

    @Override
    public List<CommentDTO> getAllCommentsForTask(Integer idTask) {
        List<Comment> commentList = commentRepository.findAllByTask(idTask);
        return commentList.stream().map(CommentDTO :: fromComment).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllCommentsForAuthor(String username) {
        List<Comment> commentList = commentRepository.findAllByAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден")).getId());
        return commentList.stream().map(CommentDTO :: fromComment).collect(Collectors.toList());

    }
}
