package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.ExerciseCategory;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.w7.sweatlog_backend.entity.Post.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ExerciseCategory category;
    private String name;
    private Double weight;
    private Integer reps;
    private UserDto user; //사용자 정보
    private Integer duration;



    public static PostResponse from(Post post) {

        return PostResponse.builder()
                .id(post.getId())
                .date(post.getDate())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .category(post.getCategory())
                .name(post.getName())
                .weight(post.getWeight())
                .reps(post.getReps())
                .duration(post.getDuration())
                .user(UserDto.from(post.getUser()))
                .build();
    }


}
