package com.w7.sweatlog_backend.dto;

import com.w7.sweatlog_backend.entity.enums.ExerciseCategory;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.PostDetail;
import com.w7.sweatlog_backend.entity.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private UserDto user; //사용자 정보
    private String memo;
    private String imageUrl;
    private List<PostDetailResponse> details;
    private boolean isLiked;
    private Long LikeCount;
    private Long CommentCount;


    public static PostResponse from(Post post) {

        List<PostDetailResponse> detailResponses = new ArrayList<>();

        //Post 에 PostDetail을 순회하며  PostResponse DTO로 변환
        for (PostDetail detail : post.getDetails()) {
            PostDetailResponse detailResponse = PostDetailResponse.from(detail);
            detailResponses.add(detailResponse);
        }

        return PostResponse.builder()
                .id(post.getId())
                .date(post.getDate())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .category(post.getCategory())
                .user(UserDto.from(post.getUser()))
                .imageUrl(post.getImageUrl())
                .memo(post.getMemo())
                .details(detailResponses)
                .build();
    }


}
