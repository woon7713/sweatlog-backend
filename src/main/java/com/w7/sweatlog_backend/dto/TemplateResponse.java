package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponse {

    private Long id;
    private List<TemplateDetailResponse> details;
    private UserDto user;
    private String purposeName;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static TemplateResponse from(Template template) {

        List<TemplateDetailResponse> detailResponses = new ArrayList<>();

        //Post 에 PostDetail을 순회하며  PostResponse DTO로 변환
        for (TemplateDetail detail : template.getDetails()) {
            TemplateDetailResponse detailResponse = TemplateDetailResponse.from(detail);
            detailResponses.add(detailResponse);
        }

        return TemplateResponse.builder()
                .id(template.getId())
                .purposeName(template.getPurposeName())
                .createAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .details(detailResponses)
                .user(UserDto.from(template.getUser()))
                .build();
    }
}
