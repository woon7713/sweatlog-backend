package com.w7.sweatlog_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoutineRequest {

    @NotBlank(message = "Routine Name is required")
    private String routineName;
    private List<RoutineDetailRequest> details;


}
