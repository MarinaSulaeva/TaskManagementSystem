package com.testtask.TaskManagementSystem.exceptions;

public class CommentDoesNotBelongToUserException extends RuntimeException {
    public CommentDoesNotBelongToUserException(String message) {
        super(message);
    }
}
