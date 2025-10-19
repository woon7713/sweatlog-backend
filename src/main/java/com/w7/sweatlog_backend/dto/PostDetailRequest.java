package com.w7.sweatlog_backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDetailRequest {

    @NotBlank(message = "운동 이름은 필수입니다.")
    private String name;

    // 웨이트 트레이닝용 필드
    private Integer weight;
    private Integer reps;
    private Integer sets;

    // 유산소/기타 운동용 필드
    private Integer duration;
}
