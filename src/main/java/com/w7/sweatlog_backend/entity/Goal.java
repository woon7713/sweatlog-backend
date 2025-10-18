package com.w7.sweatlog_backend.entity;

import com.w7.sweatlog_backend.entity.WorkoutCode;
import com.w7.sweatlog_backend.entity.enums.GoalStatus;
import com.w7.sweatlog_backend.entity.enums.GoalType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // 일부 목표(체중, 체형 등)는 운동코드가 필요 없을 수 있으므로 nullable 허용
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "workout_id", nullable = true)
    private WorkoutCode workout;

    @Column(name = "start_dt", nullable = false)
    private LocalDate startDt;

    @Column(name = "end_dt", nullable = false)
    private LocalDate endDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private GoalStatus goalStatus;

    // 목표 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30)
    private GoalType goalType;

    @Column(name = "target_weight_kg", precision = 6, scale = 2)
    private BigDecimal targetWeightKg;

    @Column(name = "target_reps")
    private Integer targetReps;

    @Column(name = "target_lift_weight_kg", precision = 6, scale = 2)
    private BigDecimal targetLiftWeightKg;

    @Column(name = "target_distance_m")
    private Integer targetDistanceM;

    @Column(name = "target_duration_sec")
    private Integer targetDurationSec;

    @Column(name = "target_sessions_per_week")
    private Integer targetSessionsPerWeek;

    @Column(name = "target_body_fat_pct", precision = 5, scale = 2)
    private BigDecimal targetBodyFatPct;

    @Column(name = "target_muscle_mass_kg", precision = 6, scale = 2)
    private BigDecimal targetMuscleMassKg;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}