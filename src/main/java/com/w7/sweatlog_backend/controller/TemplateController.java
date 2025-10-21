package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.RoutineDetailRequest;
import com.w7.sweatlog_backend.dto.RoutineRequest;
import com.w7.sweatlog_backend.dto.RoutineResponse;
import com.w7.sweatlog_backend.entity.Template;
import com.w7.sweatlog_backend.service.RoutineService;
import com.w7.sweatlog_backend.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routine/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final RoutineService routineService;



    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        List<Template> templates = templateService.findAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @PostMapping("/{templateId}/toRoutine")
    public ResponseEntity<RoutineResponse> fromTemplateToRoutine(@PathVariable Long templateId,@RequestBody RoutineRequest request) {

        // TemplateDetail 목록을 RoutineDetailRequest DTO 목록으로 변환
        List<RoutineDetailRequest> routineDetails = templateService.templateToRoutine(templateId);
        //RoutineRequest detail에 저장하여 루틴 으로 생성
        request.setDetails(routineDetails);

        //생성
        RoutineResponse savedRoutine = routineService.createRoutine(request);
        return ResponseEntity.ok(savedRoutine);
    }
}
