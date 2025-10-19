package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.PostDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {

    private Long id;
    private String name;
    private Integer weight;
    private Integer reps;
    private Integer sets;
    private Integer duration;

    public static PostDetailResponse from(PostDetail detail) {
        return PostDetailResponse.builder()
                .id(detail.getId())
                .name(detail.getName())
                .weight(detail.getWeight())
                .reps(detail.getReps())
                .sets(detail.getSets())
                .duration(detail.getDuration())
                .build();
    }
}
