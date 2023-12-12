package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для создания или изменения комментария
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateComment {
    String text;
}
