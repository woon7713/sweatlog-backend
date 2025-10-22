package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.UserDto;
import com.w7.sweatlog_backend.entity.enums.Day;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RoutineRequest {

    @NotBlank(message = "Routine Name is required")
    private String routineName;

    private List<RoutineDetailRequest> details;


}
