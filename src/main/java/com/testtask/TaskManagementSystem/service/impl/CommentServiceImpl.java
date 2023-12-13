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
import java.util.Objects;
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
     * Метод для проверки наличия задачи в базе данных с переданным id
     */
    private boolean checkTaskIsExist(Integer id) {
        Task task = taskRepository.findById(id).orElse(null);
        return !Objects.isNull(task);
    }

    /**
     * Класс создания комментария
     */
    @Override
    public Integer createComment(String username, Integer idTask, CreateOrUpdateComment newComment) {
        if (checkTaskIsExist(idTask)) {
            Comment comment = new Comment();
            comment.setCreatedAt(LocalDateTime.now());
            comment.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
            comment.setTask(taskRepository.findById(idTask).orElseThrow(() -> new TaskNotFoundException("задача не найдена")));
            comment.setText(newComment.getText());
            return commentRepository.save(comment).getId();
        } else {
            throw new TaskNotFoundException("задача не найдена");
        }

    }

    /**
     * Класс для изменения комментария
     */
    @Override
    public CommentDTO changeComment(String username,
                                    Integer idComment,
                                    CreateOrUpdateComment changeComment,
                                    Integer idTask) {
        if (checkTaskIsExist(idTask)) {
            Comment comment = checkAuthor(username, idComment);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setText(changeComment.getText());
            return CommentDTO.fromComment(commentRepository.save(comment));
        } else {
            throw new TaskNotFoundException("задача не найдена");
        }

    }

    /**
     * Метод для удаления комментария
     */
    @Override
    public void deleteComment(String username, Integer idComment, Integer idTask) {
        if (checkTaskIsExist(idTask)) {
            commentRepository.delete(checkAuthor(username, idComment));
        } else {
            throw new TaskNotFoundException("задача не найдена");
        }
    }

    /**
     * Класс для получения комментария
     */
    @Override
    public CommentDTO getComment(Integer idComment, Integer idTask) {
        if (checkTaskIsExist(idTask)) {
            return CommentDTO.fromComment(commentRepository.findById(idComment).orElseThrow(() -> new CommentNotFoundException("комментарий не найден")));
        } else {
            throw new TaskNotFoundException("задача не найдена");
        }
    }

    /**
     * Метод для получения всех комментариев для задачи
     */
    @Override
    public List<CommentDTO> getAllCommentsForTask(Integer idTask, Integer page) {
        if (checkTaskIsExist(idTask)) {
            List<Comment> commentList = commentRepository.findAllByTask(idTask, PageRequest.of(page, 10)).stream().toList();
            return commentList.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
        } else {
            throw new TaskNotFoundException("задача не найдена");
        }
    }

    /**
     * Метод для получения всех комментариев автора
     */
    @Override
    public List<CommentDTO> getAllCommentsForAuthor(String username, Integer page) {
        List<Comment> commentList = commentRepository.findAllByAuthor(
                        usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден")).getId(),
                        PageRequest.of(page, 10))
                .stream().toList();
        return commentList.stream().map(CommentDTO::fromComment).collect(Collectors.toList());
    }
}
