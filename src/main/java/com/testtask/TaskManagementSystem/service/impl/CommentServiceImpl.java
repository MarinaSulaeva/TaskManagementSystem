package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.exceptions.CommentDoesNotBelongToUserException;
import com.testtask.TaskManagementSystem.exceptions.CommentNotFoundException;
import com.testtask.TaskManagementSystem.exceptions.TaskNotFoundException;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.CommentRepository;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-сервис для работы с комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;


    /**
     * Метод для проверки прав пользователя для изменения комментария
     */
    private Comment checkAuthor(String username, Integer idComment) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(() -> new CommentNotFoundException("комментарий не найден"));
        if (!comment.getAuthor().equals(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден")))) {
            throw new CommentDoesNotBelongToUserException("комментарий принадежит другому пользователю");
        }
        return comment;
    }


    /**
     * Класс создания комментария
     */
    @Override
    public Integer createComment(String username, Integer idTask, CreateOrUpdateComment newComment) {
        Task task = taskRepository.findById(idTask).orElseThrow(() ->new TaskNotFoundException("задача не найдена"));
        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        comment.setTask(task);
        comment.setText(newComment.getText());
        return commentRepository.save(comment).getId();
    }

    /**
     * Класс для изменения комментария
     */
    @Override
    public CommentDTO changeComment(String username,
                                    Integer idComment,
                                    CreateOrUpdateComment changeComment,
                                    Integer idTask) {
        Task task = taskRepository.findById(idTask).orElseThrow(() ->new TaskNotFoundException("задача не найдена"));
        Comment comment = checkAuthor(username, idComment);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setText(changeComment.getText());
        return CommentDTO.fromComment(commentRepository.save(comment));
    }

    /**
     * Метод для удаления комментария
     */
    @Override
    public void deleteComment(String username, Integer idComment, Integer idTask) {
        Task task = taskRepository.findById(idTask).orElseThrow(() ->new TaskNotFoundException("задача не найдена"));
        commentRepository.delete(checkAuthor(username, idComment));
    }

    /**
     * Класс для получения комментария
     */
    @Override
    public CommentDTO getComment(Integer idComment, Integer idTask) {
        Task task = taskRepository.findById(idTask).orElseThrow(() ->new TaskNotFoundException("задача не найдена"));
        return CommentDTO.fromComment(commentRepository.findById(idComment).orElseThrow(() -> new CommentNotFoundException("комментарий не найден")));
    }

    /**
     * Метод для получения всех комментариев для задачи
     */
    @Override
    public List<CommentDTO> getAllCommentsForTask(Integer idTask, Integer page) {
        Task task = taskRepository.findById(idTask).orElseThrow(() ->new TaskNotFoundException("задача не найдена"));
        List<Comment> commentList = commentRepository.findByTask(task, PageRequest.of(page, 10)).stream().toList();
        return commentList.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
    }

    /**
     * Метод для получения всех комментариев автора
     */
    @Override
    public List<CommentDTO> getAllCommentsForAuthor(String username, Integer page) {
        List<Comment> commentList = commentRepository.findByAuthor(
                        usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден")),
                        PageRequest.of(page, 10))
                .stream().toList();
        return commentList.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
    }
}
