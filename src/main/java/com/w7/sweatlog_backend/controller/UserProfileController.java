package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.*;
import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.service.ProfileService;
import com.w7.sweatlog_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    // 프로필 정보 설정
    @PutMapping("/setting")
    public ResponseEntity<ProfileResponse> setInitialSetting(@RequestBody @Valid ProfileUpsertRequest request) {
        return ResponseEntity.ok(profileService.upsertProfile(request));
    }

    @GetMapping("/goals")
    public ResponseEntity<List<GoalResponseDto>> myGoals() {
        return ResponseEntity.ok(userService.getMyGoals());
    }

    @PostMapping("/goals")
    public ResponseEntity<GoalResponseDto> createGoal(@RequestBody @Valid GoalCreateRequest req) {
        return ResponseEntity.ok(userService.setInitialSettings(req));
    }

    @PatchMapping("/goals/{id}/status")
    public ResponseEntity<GoalResponseDto> updateGoalStatus(@PathVariable Long id, @RequestParam GoalStatus status) {
        return ResponseEntity.ok(userService.updateGoalStatus(id, status));
    }

}
