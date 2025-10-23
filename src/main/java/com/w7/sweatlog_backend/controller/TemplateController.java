package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.*;
import com.w7.sweatlog_backend.entity.Template;
import com.w7.sweatlog_backend.service.RoutineService;
import com.w7.sweatlog_backend.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routine/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final RoutineService routineService;


//템플릿 생성
    @PostMapping
    public ResponseEntity<TemplateResponse> createRoutine(@Valid @RequestBody TemplateRequest request) {
        TemplateResponse response = templateService.createTemplate(request);
        return ResponseEntity.ok(response);
    }


    //나의 템플릿 출력
    @GetMapping
    public ResponseEntity<Page<TemplateResponse>> getMyTemplate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TemplateResponse> templates = templateService.findAllTemplates(pageable);
        return ResponseEntity.ok(templates);

    }



    //루틴 수정
    @PutMapping("/{templateId}")
    public ResponseEntity<TemplateResponse> updateTemplate(
            @PathVariable Long templateId,
            @Valid @RequestBody TemplateRequest request
    ) {
        TemplateResponse response = templateService.updatedTemplate(templateId, request);
        return ResponseEntity.ok(response);
    }

    //루틴 삭제
    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long templateId) {

        templateService.deleteTemplate(templateId);
        return ResponseEntity.noContent().build();
    }

    //루틴으로 변환
    @PostMapping("/{templateId}/toRoutine")
    public ResponseEntity<RoutineResponse> fromTemplateToRoutine(@PathVariable Long templateId) {

        // Template을 RoutineRequest DTO 목록으로 변환
       RoutineRequest routineRequest = templateService.templateToRoutine(templateId);


        //생성
        RoutineResponse savedRoutine = routineService.createRoutine(routineRequest);
        return ResponseEntity.ok(savedRoutine);
    }
}
