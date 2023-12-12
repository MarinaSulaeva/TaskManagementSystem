package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Класс-обертка для получения комметариев
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private String author;
    private String taskName;
    private LocalDateTime createdAt;
    private String text;

    /**
     * Метод маппинга из сущности
     */
    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getAuthor().getUsername(), comment.getTask().getTitle(), comment.getCreatedAt(), comment.getText());
    }
}
