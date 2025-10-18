package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.ActivityLevel;
import com.w7.sweatlog_backend.entity.enums.ExperienceLevel;
import com.w7.sweatlog_backend.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long userId;

    private ExperienceLevel experienceLevel;
    private ActivityLevel activityLevel;
    private List<Long> preferredWorkoutIds;

    private Gender gender;
    private LocalDate birthDate;
    private Integer heightCm;
    private BigDecimal weightKg;

    // 업서트 중 최초 목표를 만들었다면 반환 (없으면 null)
    private GoalResponseDto createdGoal;
}