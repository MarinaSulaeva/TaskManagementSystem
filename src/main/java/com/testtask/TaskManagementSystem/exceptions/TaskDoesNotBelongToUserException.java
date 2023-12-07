package com.testtask.TaskManagementSystem.exceptions;
public class TaskDoesNotBelongToUserException extends RuntimeException{
    public TaskDoesNotBelongToUserException(String message) {
        super(message);
    }
}
