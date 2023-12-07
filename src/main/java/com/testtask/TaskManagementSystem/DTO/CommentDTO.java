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
    private Users author;
    private Task task;
    private LocalDateTime createdAt;
    private String text;

    public Comment toComment() {
        return new Comment(this.id, this.author, this.task, this.createdAt, this.text);
    }

    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getAuthor(), comment.getTask(), comment.getCreatedAt(), comment.getText());
    }
}
