package com.w7.sweatlog_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TemplateDetailRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message="order is required")
    private Integer orderNumber;

    private Integer time;
    private Integer rep;
    private Integer set;
}
