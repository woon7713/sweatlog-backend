package com.w7.sweatlog_backend.repository;

import com.w7.sweatlog_backend.entity.Routine;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.entity.enums.Day;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    Page<Routine> findByUser(User user, Pageable pageable);

}
