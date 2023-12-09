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
public class TaskForChange {
    @NotNull
    private Integer id;
    @Size(min = 2, max = 32)
    private String title;
    @Size(min = 2, max = 255)
    private String description;
    private Priority priority;

    public Task toTask() {
        Task task = new Task();
        task.setId(this.getId());
        task.setPriority(this.priority);
        task.setTitle(this.title);
        task.setDescription(this.description);
        return task;
    }

    public static TaskForChange fromTask(Task task) {
        return new TaskForChange(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority());
    }

}
