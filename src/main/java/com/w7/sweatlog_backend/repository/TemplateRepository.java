package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    //퍼블릭과 현재 접속한 사용자의 템플릿만 조회
    Page<Template> findByUserIdOrUserId(Long publicUserId, Long currentUserId, Pageable pageable);

}
