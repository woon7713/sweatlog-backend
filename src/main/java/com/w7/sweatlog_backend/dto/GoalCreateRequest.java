package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.entity.enums.GoalType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GoalCreateRequest {
    private Long workoutId;            // STRENGTH 등에서 필요, 다른 타입은 null 가능 // WorkoutCode.id
    private LocalDate startDt;
    private LocalDate endDt;
    private GoalStatus status;

    private GoalType type;

    private BigDecimal targetWeightKg;
    private Integer targetReps;
    private BigDecimal targetLiftWeightKg;
    private Integer targetDistanceM;
    private Integer targetDurationSec;
    private Integer targetSessionsPerWeek;
    private BigDecimal targetBodyFatPct;
    private BigDecimal targetMuscleMassKg;
}