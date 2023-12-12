package com.testtask.TaskManagementSystem.exceptions;

/**
 * Класс-исключение в случае, когда пользователя нет в базе данных
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
