package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.ActivityLevel;
import com.w7.sweatlog_backend.entity.enums.ExperienceLevel;
import com.w7.sweatlog_backend.entity.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ProfileUpsertRequest {
    @NotNull
    private ExperienceLevel experienceLevel; // 운동 경력/경험 기반 분류

    @NotNull
    private ActivityLevel activityLevel; // 서비스 활동 기반 분류

    @NotEmpty
    private List<Long> preferredWorkoutIds; // WorkoutCode.id 목록

    private Gender gender;         // MALE/FEMALE
    private LocalDate birthDate;   // yyyy-MM-dd
    private Integer heightCm;
    private BigDecimal weightKg;

}
