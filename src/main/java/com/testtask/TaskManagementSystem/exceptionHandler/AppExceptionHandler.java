package com.testtask.TaskManagementSystem.exceptionHandler;

import com.testtask.TaskManagementSystem.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс для обработки исключений
 */
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = {CommentNotFoundException.class})
    public ResponseEntity<?> handleCommentNotFound(CommentNotFoundException commentNotFoundException) {
        return new ResponseEntity<>("комментарий не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleCommentDoesNotBelongToUser(CommentDoesNotBelongToUserException commentDoesNotBelongToUserException) {
        return new ResponseEntity<>("комментарий не принадлежит пользователю", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException taskNotFoundException) {
        return new ResponseEntity<>("задача не найдена", HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {TaskDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleTaskDoesNotBelongToUser(TaskDoesNotBelongToUserException taskDoesNotBelongToUserException) {
        return new ResponseEntity<>("данная задача не принадлежит пользователю", HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("пользователь не найден", HttpStatus.NOT_FOUND);
    }
}
