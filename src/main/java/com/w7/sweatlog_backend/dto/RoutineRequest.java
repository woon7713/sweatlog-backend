package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.UserDto;
import com.w7.sweatlog_backend.entity.enums.Day;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoutineRequest {


    private List<RoutineDetailRequest> details;

    @NotNull(message="day is required")
    private Day day;



}
