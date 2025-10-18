package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.ExerciseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PostRequest {

    @NotNull(message = "운동 날짜를 선택해주세요.")
    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    @NotNull(message = "운동 종류를 선택해주세요.")
    private ExerciseCategory category;

    @NotBlank(message = "운동1 (예: 벤치프레스)")
    private String name;


    private Double weight;
    private Integer reps;     // 횟수
    private Integer duration; // 요가/유산소/필라테스용

    @Size(max = 500, message = "Memo must not exceed 500 characters")
    private String memo;

    private String imageUrl;  // 운동 인증 이미지 URL










}
