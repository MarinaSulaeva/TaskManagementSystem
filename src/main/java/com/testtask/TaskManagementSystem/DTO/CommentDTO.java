package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private String author;
    private String taskName;
    private LocalDateTime createdAt;
    private String text;

    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getAuthor().getUsername(), comment.getTask().getTitle(), comment.getCreatedAt(), comment.getText());
    }
}
