package com.testtask.TaskManagementSystem.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
