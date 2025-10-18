package com.w7.sweatlog_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date; // 운동 날짜

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;     // 시작 시간

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;       // 종료 시간

    // 운동 종류
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseCategory category;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name; // 운동 이름

    @Column(name = "weight")
    private Double weight; // kg (웨이트 전용)

    @Column(name = "reps")
    private Integer reps; // 횟수 (웨이트 전용)

    @Column(name = "duration")
    private Integer duration; // 운동 시간 (요가, 필라테스, 유산소 전용)

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;  // 운동 인증 이미지 URL

    @Column(name="memo")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean deleted = false;

    //생성 날짜 -> 논의 좀
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "_at")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Like> likes = new HashSet<>();

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Comment> comments = new HashSet<>();
}