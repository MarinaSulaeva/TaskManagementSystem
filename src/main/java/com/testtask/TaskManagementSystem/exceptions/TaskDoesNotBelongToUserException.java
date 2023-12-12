package com.testtask.TaskManagementSystem.exceptions;
/**
 * Класс-исключение в случае, когда у пользователя нет прав дл яизменения задачи
 */
public class TaskDoesNotBelongToUserException extends RuntimeException{
    public TaskDoesNotBelongToUserException(String message) {
        super(message);
    }
}
