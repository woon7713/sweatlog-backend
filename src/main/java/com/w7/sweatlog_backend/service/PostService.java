package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.PostRequest;
import com.w7.sweatlog_backend.dto.PostResponse;
import com.w7.sweatlog_backend.entity.ExerciseCategory;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.exception.ResourceNotFoundException;
import com.w7.sweatlog_backend.exception.UnauthorizedException;
import com.w7.sweatlog_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    //운동 기록 생성
    public PostResponse createPost(PostRequest request) {

        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .category(request.getCategory())
                .name(request.getName())
                .user(currentUser)
                .memo(request.getMemo())
                .imageUrl(request.getImageUrl())
                .build();

        // 카테고리 별 필드 세팅 -> 웨이트 트레이닝: 중량, 횟수 / 필라세트 요가 유산소 운동시간 표시
        if (request.getCategory() == ExerciseCategory.WEIGHT_TRAINING) {
            post.setWeight(request.getWeight());
            post.setReps(request.getReps());
        } else {
            post.setDuration(request.getDuration());
        }

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }



    //게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findAllActive(pageable);
        return posts.map(post -> {
            PostResponse response = PostResponse.from(post);
            //Like 갯수
            //Like 여부
            //메모 갯수
            return response;
        });
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getUserPosts(Long userId, Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findByUserIdAndNotDeleted(userId, pageable);
        return posts.map(post -> {
            PostResponse response = PostResponse.from(post);
            //Like 갯수
            //Like 여부
            //댓글 갯수
            return response;
        });
    }

    // 운동 기록 삭제 (soft delete)
    public void deletePost(Long postId) {
        //인증된 유저확인
        User currentUser = userService.getCurrentUser();

        //운동 기록 확인
        Post post = postRepository.findByIdAndNotDeleted(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not found"));

        // 권한 확인
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("게시물을 삭제할 권한이 없습니다");
        }
        post.setDeleted(true);
        postRepository.save(post);
    }

    // 운동 기록  수정
    public PostResponse updatePost(Long postId, PostRequest request) {

        //인증된 유저확인
        User currentUser = userService.getCurrentUser();

        //운동 기록 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // 권한 확인
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this post");
        }

        //운동 기록 수정
        post.setName(request.getName());
        post.setDate(request.getDate());
        post.setStartTime(request.getStartTime());
        post.setEndTime(request.getEndTime());
        post.setCategory(request.getCategory());
        post.setMemo(request.getMemo());
        post.setImageUrl(request.getImageUrl());

        //카테고리별 수정
        if (request.getCategory() == ExerciseCategory.WEIGHT_TRAINING) {
            post.setWeight(request.getWeight());
            post.setReps(request.getReps());
            post.setDuration(null);
        } else {
            post.setDuration(request.getDuration());
            post.setWeight(null);
            post.setReps(null);
        }

        //저장 후 반환
        Post updatedPost = postRepository.save(post);
        return PostResponse.from(updatedPost);
    }

}
