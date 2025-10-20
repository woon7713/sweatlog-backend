package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.Comment;
import com.w7.sweatlog_backend.entity.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private UserDto user;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(UserDto.from(comment.getUser()))
                .createdAt(comment.getCreatedAt())
                .build();

    }

}
