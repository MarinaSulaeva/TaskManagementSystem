package com.testtask.TaskManagementSystem.exceptionHandler;

import com.testtask.TaskManagementSystem.exceptions.CommentDoesNotBelongToUserException;
import com.testtask.TaskManagementSystem.exceptions.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommentExceptionHandler {
    @ExceptionHandler(value = {CommentNotFoundException.class})
    public ResponseEntity<?> handleCommentNotFound(CommentNotFoundException commentNotFoundException) {
        return new ResponseEntity<>("комментарий не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleCommentDoesNotBelongToUser(CommentDoesNotBelongToUserException commentDoesNotBelongToUserException) {
        return new ResponseEntity<>("комментарий не принадлежит пользователю", HttpStatus.FORBIDDEN);
    }
}
