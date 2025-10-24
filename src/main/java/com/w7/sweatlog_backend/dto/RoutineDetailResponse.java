package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.Routine;
import com.w7.sweatlog_backend.entity.RoutineDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDetailResponse {
    private Long id;
    private String name;
    private Integer orderNumber;
    private Integer time;
    private Integer rep;
    private Integer set;


    public static RoutineDetailResponse from(RoutineDetail detail) {

        return RoutineDetailResponse.builder()
                .id(detail.getId())
                .name(detail.getName())
                .orderNumber(detail.getOrderNumber())
                .set(detail.getSet())
                .rep(detail.getRep())
                .time(detail.getTime())
                .build();
    }
}
