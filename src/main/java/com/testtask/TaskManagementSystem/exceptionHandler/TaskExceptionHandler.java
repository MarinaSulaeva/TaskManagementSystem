package com.testtask.TaskManagementSystem.exceptionHandler;

import com.testtask.TaskManagementSystem.exceptions.TaskDoesNotBelongToUserException;
import com.testtask.TaskManagementSystem.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler {
    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException taskNotFoundException) {
        return new ResponseEntity<>("задача не найдена", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TaskDoesNotBelongToUserException.class})
    public ResponseEntity<?> handleTaskDoesNotBelongToUser(TaskDoesNotBelongToUserException taskDoesNotBelongToUserException) {
        return new ResponseEntity<>("данная задача не принадлежит пользователю", HttpStatus.FORBIDDEN);
    }
}
