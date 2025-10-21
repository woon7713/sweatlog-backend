package com.w7.sweatlog_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.w7.sweatlog_backend.entity.enums.Day;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "templates")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String purposeName; // 템플릿 이름 (예: 초보자 전신 근력)


    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference           //무한참조방지
    private List<TemplateDetail> details = new ArrayList<>();

    // 연관 관계 편의 메서드
    public void addDetail(TemplateDetail detail) {
        this.details.add(detail);
        detail.setTemplate(this);
    }





}
