package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.ExerciseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PostRequest {

    @NotNull(message = "운동 날짜를 선택해주세요.")
    private LocalDate date;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private LocalTime startTime;

    private LocalTime endTime;

    @NotNull(message = "운동 종류를 선택해주세요.")
    private ExerciseCategory category;


    @Size(max = 500, message = "Memo must not exceed 500 characters")
    private String memo;

    private String imageUrl;  // 운동 인증 이미지 URL

    private List<PostDetailRequest> details;










}
