package com.w7.sweatlog_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Entity
@Table(name= "post_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name; // 운동 이름

    @Column(name = "weight")
    private Integer weight; // kg (웨이트 전용)

    @Column(name = "reps")
    private Integer reps; // 횟수 (웨이트 전용)

    @Column(name = "duration")
    private Integer duration; // 운동 시간 초 단위(요가, 필라테스, 유산소 전용)

    @Column(name = "sets")
    private Integer sets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;
}
