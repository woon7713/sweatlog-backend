// ===== file: src/main/java/com/w7/sweatlog_backend/service/ProfileService.java =====
package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.*;
import com.w7.sweatlog_backend.entity.Goal;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.entity.WorkoutCode;
import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.repository.GoalRepository;
import com.w7.sweatlog_backend.repository.UserRepository;
import com.w7.sweatlog_backend.repository.WorkoutCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final WorkoutCodeRepository workoutCodeRepository;
    private final GoalRepository goalRepository;

    // 초기, 수정
    @Transactional
    public ProfileResponse upsertProfile(ProfileUpsertRequest req) {
        User me = userService.getCurrentUser();

        // 1) 프로필 설정
        if (req.getExperienceLevel() != null) me.setExperienceLevel(req.getExperienceLevel());
        if (req.getActivityLevel() != null)   me.setActivityLevel(req.getActivityLevel());

        if (req.getGender() != null)     me.setGender(req.getGender());
        if (req.getBirthDate() != null)  me.setBirthDate(req.getBirthDate());
        if (req.getHeightCm() != null)   me.setHeightCm(req.getHeightCm());
        if (req.getWeightKg() != null)   me.setWeightKg(req.getWeightKg());

        if (req.getFullName() != null) me.setFullName(req.getFullName());
        if (req.getBio()      != null) me.setBio(req.getBio());

        // 2) 선호 운동
        if (req.getPreferredWorkoutIds() != null) {
            List<WorkoutCode> codes = workoutCodeRepository.findAllById(req.getPreferredWorkoutIds());
            if (codes.size() != req.getPreferredWorkoutIds().size()) {
                throw new IllegalArgumentException("invalid workoutId exists");
            }
            me.getPreferredWorkouts().clear();
            me.getPreferredWorkouts().addAll(codes);
        }

        GoalResponseDto created = null;
        //userRepository.save(me);

        User updatedUser = userRepository.save(me);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                updatedUser,
                updatedUser.getPassword(),
                updatedUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<Long> preferredIds = me.getPreferredWorkouts().stream()
                .map(WorkoutCode::getId).toList();

        return ProfileResponse.builder()
                .userId(updatedUser.getId())
                .experienceLevel(updatedUser.getExperienceLevel())
                .activityLevel(updatedUser.getActivityLevel())
                .preferredWorkoutIds(preferredIds)
                .gender(updatedUser.getGender())
                .birthDate(updatedUser.getBirthDate())
                .heightCm(updatedUser.getHeightCm())
                .weightKg(updatedUser.getWeightKg())
                .createdGoal(created)
                .build();
    }
}
