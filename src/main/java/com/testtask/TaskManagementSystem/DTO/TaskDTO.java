package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Users author;
    private Users executor;


    public Task toTask() {
        return new Task(this.id,
                this.title,
                this.description,
                this.status,
                this.priority,
                this.author,
                this.executor);
    }

    public static TaskDTO fromTask(Task task) {
        return new TaskDTO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getAuthor(),
                task.getExecutor());
    }
}
