package com.testtask.TaskManagementSystem.DTO;


import com.testtask.TaskManagementSystem.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

/**
 * Класс для создания задачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskForCreate {

    @Size(min = 2, max = 32)
    private String title;
    @Size(min = 2, max = 255)
    private String description;
    private Priority priority;
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String executorUsername;

    /**
     * Метод для преобразования в сущность
     */
    public Task toTask() {
        Task task = new Task();
        task.setPriority(this.priority);
        task.setTitle(this.title);
        task.setDescription(this.description);
        return task;
    }


}
