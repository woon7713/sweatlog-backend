package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.Goal;
import com.w7.sweatlog_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long>{
    List<Goal> findByUserOrderByCreatedAtDesc(User user);

}
