package com.testtask.TaskManagementSystem.DTO;


import com.testtask.TaskManagementSystem.entity.Task;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    @NotNull
    private Integer id;
    @Size(min = 2, max = 32)
    private String title;
    @Size(min = 2, max = 255)
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
