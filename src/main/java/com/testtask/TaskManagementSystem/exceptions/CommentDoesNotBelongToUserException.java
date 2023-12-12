package com.testtask.TaskManagementSystem.exceptions;

/**
 * Класс-исключение в случее, когда у польщователя нет прав для изменения комментария
 */
public class CommentDoesNotBelongToUserException extends RuntimeException {
    public CommentDoesNotBelongToUserException(String message) {
        super(message);
    }
}
