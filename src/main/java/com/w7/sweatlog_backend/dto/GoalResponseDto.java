package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.Goal;
import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.entity.enums.GoalType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GoalResponseDto {
    private Long id;
    private Long workoutId;
    private String workoutName;
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

    private LocalDateTime createdAt;

    public static GoalResponseDto from(Goal g) {
        return GoalResponseDto.builder()
                .id(g.getId())
                .workoutId(g.getWorkout() != null ? g.getWorkout().getId() : null)
                .workoutName(g.getWorkout() != null ? g.getWorkout().getName() : null)
                .startDt(g.getStartDt())
                .endDt(g.getEndDt())
                .status(g.getGoalStatus())
                .type(g.getGoalType())
                .targetWeightKg(g.getTargetWeightKg())
                .targetReps(g.getTargetReps())
                .targetLiftWeightKg(g.getTargetLiftWeightKg())
                .targetDistanceM(g.getTargetDistanceM())
                .targetDurationSec(g.getTargetDurationSec())
                .targetSessionsPerWeek(g.getTargetSessionsPerWeek())
                .targetBodyFatPct(g.getTargetBodyFatPct())
                .targetMuscleMassKg(g.getTargetMuscleMassKg())
                .createdAt(g.getCreatedAt())
                .build();
    }
}
