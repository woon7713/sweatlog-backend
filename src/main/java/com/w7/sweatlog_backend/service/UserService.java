package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.GoalCreateRequest;
import com.w7.sweatlog_backend.dto.GoalResponseDto;
import com.w7.sweatlog_backend.entity.Goal;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.entity.UserDto;
import com.w7.sweatlog_backend.entity.WorkoutCode;
import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.entity.enums.GoalType;
import com.w7.sweatlog_backend.exception.AuthenticationException;
import com.w7.sweatlog_backend.repository.GoalRepository;
import com.w7.sweatlog_backend.repository.UserRepository;
import com.w7.sweatlog_backend.repository.WorkoutCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final WorkoutCodeRepository workoutCodeRepository;

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::from);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AuthenticationException("로그인이 필요합니다.");
        }

        String key = (auth.getPrincipal() instanceof UserDetails ud) ? ud.getUsername() : auth.getName();

        return userRepository.findByUsername(key)
                .or(() -> userRepository.findByEmail(key))
                .orElseThrow(() -> new AuthenticationException("사용자를 찾을 수 없습니다."));
    }

    // 목표 생성
    public GoalResponseDto setInitialSettings(GoalCreateRequest request) {
        if (request.getStartDt().isAfter(request.getEndDt())) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }

        User user = getCurrentUser();

        GoalType type = Objects.requireNonNull(request.getType(), "목표 유형(type)은 필수입니다.");

        // workoutId는 일부 타입만 필요
        WorkoutCode workout = null;
        if (request.getWorkoutId() != null) {
            workout = workoutCodeRepository.findById(request.getWorkoutId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 workoutId 입니다."));
        }

        // 타입별 필드 검증
        switch (type) {
            case WEIGHT -> {
                if (request.getTargetWeightKg() == null)
                    throw new IllegalArgumentException("체중 목표는 targetWeightKg가 필요합니다.");
            }
            case STRENGTH -> {
                if (workout == null)
                    throw new IllegalArgumentException("근력 목표는 workoutId가 필요합니다.");
                if (request.getTargetLiftWeightKg() == null && request.getTargetReps() == null)
                    throw new IllegalArgumentException("근력 목표는 targetLiftWeightKg 또는 targetReps 중 하나 이상 필요합니다.");
            }
            case ENDURANCE -> {
                if (request.getTargetDistanceM() == null && request.getTargetDurationSec() == null)
                    throw new IllegalArgumentException("지구력 목표는 targetDistanceM 또는 targetDurationSec 중 하나 이상 필요합니다.");
            }
            case FREQUENCY -> {
                if (request.getTargetSessionsPerWeek() == null)
                    throw new IllegalArgumentException("빈도 목표는 targetSessionsPerWeek가 필요합니다.");
            }
            case BODY_COMPOSITION -> {
                if (request.getTargetBodyFatPct() == null && request.getTargetMuscleMassKg() == null)
                    throw new IllegalArgumentException("체형 목표는 targetBodyFatPct 또는 targetMuscleMassKg 중 하나 이상 필요합니다.");
            }
        }

        GoalStatus status = request.getStatus() != null ? request.getStatus() : GoalStatus.ACTIVE;

        Goal goal = Goal.builder()
                .user(user)
                .workout(workout) // nullable
                .startDt(request.getStartDt())
                .endDt(request.getEndDt())
                .goalStatus(status)
                .goalType(type)
                .targetWeightKg(request.getTargetWeightKg())
                .targetReps(request.getTargetReps())
                .targetLiftWeightKg(request.getTargetLiftWeightKg())
                .targetDistanceM(request.getTargetDistanceM())
                .targetDurationSec(request.getTargetDurationSec())
                .targetSessionsPerWeek(request.getTargetSessionsPerWeek())
                .targetBodyFatPct(request.getTargetBodyFatPct())
                .targetMuscleMassKg(request.getTargetMuscleMassKg())
                .build();

        return GoalResponseDto.from(goalRepository.save(goal));
    }

    // 내 목표 리스트
    public List<GoalResponseDto> getMyGoals() {
        User user = getCurrentUser();
        return goalRepository.findByUserOrderByCreatedAtDesc(user)
                .stream().map(GoalResponseDto::from).toList();
    }

    // 목표 상태 변경
    @Transactional
    public GoalResponseDto updateGoalStatus(Long id, GoalStatus status) {
        User me = getCurrentUser();
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("goal not found"));
        if (!goal.getUser().getId().equals(me.getId())) {
            throw new AuthenticationException("본인 목표만 수정 가능");
        }
        goal.setGoalStatus(status);
        return GoalResponseDto.from(goalRepository.save(goal));
    }
}
