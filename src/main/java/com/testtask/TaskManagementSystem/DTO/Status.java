package com.testtask.TaskManagementSystem.DTO;
/**
 * Класс-перечисление статусов выполнения задач
 */
public enum Status {
    CREATED,
    STARTED,
    IN_PROGRESS,
    PENDING,
    FINISHED;

    Status() {
    }
}
