package com.w7.sweatlog_backend.entity;

import com.w7.sweatlog_backend.entity.enums.ActivityLevel;
import com.w7.sweatlog_backend.entity.enums.ExperienceLevel;
import com.w7.sweatlog_backend.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private String profileImageUrl;

    private Gender gender;
    private LocalDate birthDate;
    private Integer heightCm;
    private BigDecimal weightKg;
    private ExperienceLevel experienceLevel;
    private ActivityLevel activityLevel;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .heightCm(user.getHeightCm())
                .weightKg(user.getWeightKg())
                .experienceLevel(user.getExperienceLevel())
                .activityLevel(user.getActivityLevel())
                .build();
    }
}