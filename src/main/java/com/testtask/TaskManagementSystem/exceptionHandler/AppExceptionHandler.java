package com.testtask.TaskManagementSystem.exceptionHandler;

import com.testtask.TaskManagementSystem.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс для обработки исключений
 */
@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(value = {CommentNotFoundException.class})
    public ResponseEntity<?> handleCommentNotFound(CommentNotFoundException commentNotFoundException) {
        log.error("comment doesn't found",commentNotFoundException);
        return new ResponseEntity<>(commentNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleCommentDoesNotBelongToUser(CommentDoesNotBelongToUserException commentDoesNotBelongToUserException) {
        log.error("comment doesn't belong to user", commentDoesNotBelongToUserException);
        return new ResponseEntity<>(commentDoesNotBelongToUserException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException taskNotFoundException) {
        log.error("task doesn't found", taskNotFoundException);
        return new ResponseEntity<>(taskNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TaskDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleTaskDoesNotBelongToUser(TaskDoesNotBelongToUserException taskDoesNotBelongToUserException) {
        log.error("task doesn't belong to user", taskDoesNotBelongToUserException);
        return new ResponseEntity<>(taskDoesNotBelongToUserException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException userNotFoundException) {
        log.error("user doesn't found", userNotFoundException);
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {JwtAuthenticationException.class})
    public ResponseEntity<?> handleJwtAuthenticationException(JwtAuthenticationException jwtAuthenticationException) {
        log.error(jwtAuthenticationException.getMessage(), jwtAuthenticationException);
        return new ResponseEntity<>(jwtAuthenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
