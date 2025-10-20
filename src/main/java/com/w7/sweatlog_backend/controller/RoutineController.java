package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.PostRequest;
import com.w7.sweatlog_backend.dto.PostResponse;
import com.w7.sweatlog_backend.dto.RoutineRequest;
import com.w7.sweatlog_backend.dto.RoutineResponse;
import com.w7.sweatlog_backend.service.RoutineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    //기록 생성
    @PostMapping
    public ResponseEntity<RoutineResponse> createRoutine(@Valid @RequestBody RoutineRequest request) {
        RoutineResponse response = routineService.createRoutine(request);
        return ResponseEntity.ok(response);
    }

//루틴 전체 조회
    @GetMapping
    public ResponseEntity<Page<RoutineResponse>> getAllRoutines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoutineResponse> posts = routineService.getRoutines(pageable);
        return ResponseEntity.ok(posts);
    }



    //루틴 수정
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineResponse> updateRoutine(
            @PathVariable Long routineId,
            @Valid @RequestBody RoutineRequest request
    ) {
        RoutineResponse response = routineService.updateRoutine(routineId, request);
        return ResponseEntity.ok(response);
    }

    //루틴 삭제
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long routineId) {

        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }
}
