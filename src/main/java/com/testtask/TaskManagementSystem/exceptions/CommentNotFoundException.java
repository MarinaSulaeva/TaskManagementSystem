package com.testtask.TaskManagementSystem.exceptions;
/**
 * Класс-исключение для случая, когда комментарий не найден в базе данных
 */
public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
