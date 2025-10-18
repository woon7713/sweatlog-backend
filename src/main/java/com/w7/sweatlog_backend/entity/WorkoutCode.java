package com.w7.sweatlog_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_code")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WorkoutCode {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;            // RUN, WEIGHTS, YOGA ..

    @Column(name = "sort_no")
    private Integer sortNo;
}
