package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.Day;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TemplateRequest {

    @NotBlank(message="Name is required")
    private String PurposeName;

    private List<TemplateDetailRequest> details;



}
