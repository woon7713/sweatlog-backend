package com.w7.sweatlog_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Comment content cannot be empty")
    @Size(max = 500, message = "Comment must not exceed 500 characters")
    private String content;
}
