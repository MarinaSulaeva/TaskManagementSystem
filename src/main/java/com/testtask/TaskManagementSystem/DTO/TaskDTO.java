package com.testtask.TaskManagementSystem.DTO;


import com.testtask.TaskManagementSystem.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UsersDTO author;
    private UsersDTO executor;


    public Task toTask() {
        return new Task(this.id,
                this.title,
                this.description,
                this.status,
                this.priority,
                this.author.toUser(),
                this.executor.toUser());
    }

    public static TaskDTO fromTask(Task task) {
        return new TaskDTO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                UsersDTO.fromUser(task.getAuthor()),
                UsersDTO.fromUser(task.getExecutor()));
    }
}
