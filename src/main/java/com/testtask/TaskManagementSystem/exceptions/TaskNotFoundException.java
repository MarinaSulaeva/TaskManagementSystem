package com.testtask.TaskManagementSystem.exceptions;
/**
 * Класс-исключение в случае, когда задача не найдена в базе данных
 */
public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
