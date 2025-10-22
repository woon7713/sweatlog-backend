package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.RoutineDetail;
import com.w7.sweatlog_backend.entity.TemplateDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDetailResponse {
    private Long id;
    private String name;
    private Integer orderNumber;
    private Integer time;
    private Integer rep;
    private Integer set;

    public static TemplateDetailResponse from(TemplateDetail detail) {

        return TemplateDetailResponse.builder()
                .id(detail.getId())
                .name(detail.getName())
                .orderNumber(detail.getOrderNumber())
                .set(detail.getSet())
                .rep(detail.getRep())
                .time(detail.getTime())
                .build();
    }
}
