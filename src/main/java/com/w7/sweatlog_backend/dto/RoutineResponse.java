package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.PostDetail;
import com.w7.sweatlog_backend.entity.Routine;
import com.w7.sweatlog_backend.entity.RoutineDetail;
import com.w7.sweatlog_backend.entity.UserDto;
import com.w7.sweatlog_backend.entity.enums.Day;
import jakarta.validation.constraints.NotNull;
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
public class RoutineResponse {
    private Long id;
    private UserDto user;
    private List<RoutineDetailResponse> details;
    private String routineName;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


    public static RoutineResponse from(Routine routine) {

        List<RoutineDetailResponse> detailResponses = new ArrayList<>();

        //Post 에 PostDetail을 순회하며  PostResponse DTO로 변환
        for (RoutineDetail detail : routine.getDetails()) {
            RoutineDetailResponse detailResponse = RoutineDetailResponse.from(detail);
            detailResponses.add(detailResponse);
        }

        return RoutineResponse.builder()
                .id(routine.getId())
                .routineName(routine.getRoutineName())
                .createAt(routine.getCreatedAt())
                .updatedAt(routine.getUpdatedAt())
                .user(UserDto.from(routine.getUser()))
                .details(detailResponses)
                .build();
    }
}
