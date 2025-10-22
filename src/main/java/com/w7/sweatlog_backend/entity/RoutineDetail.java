package com.w7.sweatlog_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name= "routine_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="routine_id" , nullable = false)
    private Routine routine;

    @Column(nullable = false)
    private Integer orderNumber; // 운동 순서

    @Column(name = "name", updatable = false)
    private String name;        //운동 이름

    @Column(name = "set")
    private Integer set;

    @Column(name = "rep")
    private Integer rep;

    @Column(name = "time")
    private Integer time;


}
