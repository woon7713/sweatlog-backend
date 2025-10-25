package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.Routine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineDetailRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message="order is required")
    private Integer orderNumber;

    private Integer time;
    private Integer rep;
    private Integer set;

}
