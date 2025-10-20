package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.entity.Like;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.exception.BadRequestException;
import com.w7.sweatlog_backend.repository.LikeRepository;
import com.w7.sweatlog_backend.repository.PostRepository;
import com.w7.sweatlog_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public boolean toggleLike(Long postId) {
        User currentUser = userService.getCurrentUser();

        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new BadRequestException("Post not found"));

        boolean alreadyLike = likeRepository.existsByUserAndPost(currentUser, post);

        if (alreadyLike) {
            likeRepository.deleteByUserAndPost(currentUser, post);
            return false;
        } else {

            Like like = Like.builder()
                    .user(currentUser)
                    .post(post)
                    .build();
            likeRepository.save(like);
            return true;
        }
    }

    @Transactional(readOnly = true)
    public Long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    public boolean isLikedByCurrentUser(Long postId) {
        User currentUser = userService.getCurrentUser();

        //삭제되어있지않은 포스트를 찾고 예외처리
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new BadRequestException("Post not found"));

        return likeRepository.existsByUserAndPost(currentUser, post);
    }
}
