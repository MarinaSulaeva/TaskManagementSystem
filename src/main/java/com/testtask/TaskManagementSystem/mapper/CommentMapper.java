package com.testtask.TaskManagementSystem.mapper;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { UsersMapper.class, TaskMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    CommentDTO toDTO(Comment comment);
    List<CommentDTO> toCommentDTOList(List<Comment> comments);

}