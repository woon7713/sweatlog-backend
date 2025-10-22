package com.w7.sweatlog_backend.service;
import com.w7.sweatlog_backend.dto.PostDetailRequest;
import com.w7.sweatlog_backend.dto.PostRequest;
import com.w7.sweatlog_backend.dto.PostResponse;
import com.w7.sweatlog_backend.entity.enums.ExerciseCategory;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.PostDetail;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.exception.ResourceNotFoundException;
import com.w7.sweatlog_backend.exception.UnauthorizedException;
import com.w7.sweatlog_backend.repository.CommentRepository;
import com.w7.sweatlog_backend.repository.LikeRepository;
import com.w7.sweatlog_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    /**
     * 운동 기록 생성
     */
    public PostResponse createPost(PostRequest request) {

        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .category(request.getCategory())
                .user(currentUser)
                .memo(request.getMemo())
                .imageUrl(request.getImageUrl())  // 운동기록 이미지 URL 생성
                .build();

        // PostDetail 생성 및 Post에 연결
        List<PostDetailRequest> detailRequests = request.getDetails();

        if (detailRequests != null) {
            for (PostDetailRequest req : detailRequests) {
                PostDetail detail = createPostDetail(post, request.getCategory(), req);
                post.addDetail(detail);
            }
        }

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    /**
     * 운동 기록 전체 조회
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findAllActive(pageable);
        return posts.map(post -> {
            PostResponse response = PostResponse.from(post);

            boolean isLiked = likeRepository.existsByUserAndPost(currentUser, post);  //좋아요여부
            Long likeCount = likeRepository.countByPostId(post.getId());              //좋아요 개수
            Long commentCount = commentRepository.countByPostId(post.getId());        //댓글 개수

            response.setLiked(isLiked);
            response.setCommentCount(commentCount);
            response.setLikeCount(likeCount);

            return response;
        });
    }

    /**
     * 단일 사용자 운동 기록 조회
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> getUserPosts(Long userId, Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findByUserIdAndNotDeleted(userId, pageable);
        return posts.map(post -> {
            PostResponse response = PostResponse.from(post);

            boolean isLiked = likeRepository.existsByUserAndPost(currentUser, post);  //좋아요여부
            Long likeCount = likeRepository.countByPostId(post.getId());              //좋아요 개수
            Long commentCount = commentRepository.countByPostId(post.getId());        //댓글 개수

            response.setLiked(isLiked);
            response.setCommentCount(commentCount);
            response.setLikeCount(likeCount);
            return response;
        });
    }

    /**
     * 운동 기록 삭제
     */
    public void deletePost(Long postId) {
        //인증된 유저확인
        User currentUser = userService.getCurrentUser();

        //운동 기록 확인
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not found"));

        // 권한 확인
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this post");
        }
        post.setDeleted(true);
        postRepository.save(post);
    }

    /**
     * 운동 기록 수정
     */
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

        //기존 필드 업데이트
        post.setDate(request.getDate());
        post.setStartTime(request.getStartTime());
        post.setEndTime(request.getEndTime());
        post.setCategory(request.getCategory());
        post.setMemo(request.getMemo());
        post.setImageUrl(request.getImageUrl());

        // 이미지 URL 수정
        if (request.getImageUrl() != null) {
            post.setImageUrl(request.getImageUrl());
        }

        //  PostDetail 리스트 업데이트 (기존 삭제 후 덮어쓰기 전략)
        post.getDetails().clear();

        List<PostDetailRequest> detailRequests = request.getDetails();

        if (detailRequests != null) {
            for (PostDetailRequest req : detailRequests) {
                PostDetail detail = createPostDetail(post, request.getCategory(), req);
                post.addDetail(detail);
            }
        }

        //저장 후 반환
        Post updatedPost = postRepository.save(post);
        return PostResponse.from(updatedPost);
    }


    /**
     * 카테고리별 필드 설정
     */
    private PostDetail createPostDetail(Post post, ExerciseCategory category, PostDetailRequest req) {
        PostDetail detail = PostDetail.builder()
                .name(req.getName())
                .post(post)
                .build();

        if (category == ExerciseCategory.WEIGHT_TRAINING) {
            // 웨이트 트레이닝일 경우
            detail.setWeight(req.getWeight());
            detail.setReps(req.getReps());
            detail.setSets(req.getSets());
        } else {
            // 유산소/기타 운동일 경우 (Duration 사용)
            detail.setDuration(req.getDuration());
        }
        return detail;
    }

}
