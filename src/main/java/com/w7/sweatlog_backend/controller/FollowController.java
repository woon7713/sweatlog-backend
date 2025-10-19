package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.FollowResponse;
import com.w7.sweatlog_backend.entity.UserResponse;
import com.w7.sweatlog_backend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<FollowResponse> toggleFollow(@PathVariable Long userId) {
        FollowResponse response = followService.toggleFollow(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/follow-status")
    public ResponseEntity<FollowResponse> getFollowStatus(@PathVariable Long userId) {
        FollowResponse response = followService.getFollowStatus(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Page<UserResponse>> getFollowers(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserResponse> followers = followService.getFollowers(userId, pageable);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<UserResponse>> getFollowing(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserResponse> following = followService.getFollowing(userId, pageable);
        return ResponseEntity.ok(following);
    }
}