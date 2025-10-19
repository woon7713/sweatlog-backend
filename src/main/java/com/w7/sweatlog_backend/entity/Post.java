package com.w7.sweatlog_backend.entity;


import com.w7.sweatlog_backend.entity.enums.ExerciseCategory;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseCategory category;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String memo;

    //사진 올리기
    @Column(name = " image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id" , nullable = false)
    private User user;

    // PostDetail 연관 관계 (1:N 양방향)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PostDetail> details = new ArrayList<>();

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean deleted = false;

    @CreationTimestamp
    @Column(name ="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Comment> Comments = new HashSet<>();

    // 연관 관계 편의 메서드
    public void addDetail(PostDetail detail) {
        this.details.add(detail);
        detail.setPost(this);
    }
}