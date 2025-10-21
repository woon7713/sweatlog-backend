package com.w7.sweatlog_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "template_details")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TemplateDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer rep;

    private Integer set;

    private Integer orderNumber;

    private Integer time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    @JsonBackReference                      //무한참조방지
    private Template template;
}
