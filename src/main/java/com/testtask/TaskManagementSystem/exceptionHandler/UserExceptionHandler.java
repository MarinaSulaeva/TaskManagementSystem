package com.testtask.TaskManagementSystem.exceptionHandler;

import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("пользователь не найден", HttpStatus.NOT_FOUND);
    }

}
