package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.WorkoutCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutCodeRepository extends JpaRepository<WorkoutCode, Long> {
}
