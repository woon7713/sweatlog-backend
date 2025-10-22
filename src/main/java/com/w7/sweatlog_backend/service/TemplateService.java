package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.*;
import com.w7.sweatlog_backend.entity.*;
import com.w7.sweatlog_backend.exception.ResourceNotFoundException;
import com.w7.sweatlog_backend.exception.UnauthorizedException;
import com.w7.sweatlog_backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {

    private final  TemplateRepository templateRepository;
    private final UserService userService;

    @Value("${app.template.public-user-id}")
    private Long publicUserId;


    //템플릿 생성
    public TemplateResponse createTemplate(TemplateRequest request) {

        User currentUser = userService.getCurrentUser();

        Template template = Template.builder()
                .purposeName(request.getPurposeName())
                .user(currentUser)
                .build();

        // RoutineDetail 생성
        List<TemplateDetailRequest> detailRequests = request.getDetails();

        //RoutineDetail의 값이 있으면 반복해서 detail값 생성
        if (detailRequests != null) {
            for (TemplateDetailRequest req : detailRequests) {
                TemplateDetail detail = TemplateDetail.builder()
                        .name(req.getName())
                        .orderNumber(req.getOrderNumber())
                        .set(req.getSet())
                        .rep(req.getRep())
                        .time(req.getTime())
                        .build();

                //리스트에 추가
                template.addDetail(detail);
            }
        }
        Template updatedTemplate = templateRepository.save(template);
        return TemplateResponse.from(updatedTemplate);
    }

    //템플릿 전체 출력 -> 모든 사용자에게 출력
    public Page<TemplateResponse> findAllTemplates(Pageable pageable) {

        User currentUser = userService.getCurrentUser();

        Page<Template> templates = templateRepository.findByUserIdOrUserId(publicUserId, currentUser.getId(), pageable);

        return templates.map(TemplateResponse::from);



    }

    //템플릿 수정
    public TemplateResponse updatedTemplate(Long templateId, TemplateRequest request) {

        User currentUser = userService.getCurrentUser();

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));

        // RoutineDetail 초기화
        template.getDetails().clear();
        template.setPurposeName(request.getPurposeName());

        // 새로운 RoutineDetail 생성 및 수정
        List<TemplateDetailRequest> detailRequests = request.getDetails();

        if (detailRequests != null) {
            for (TemplateDetailRequest req : detailRequests) {
                TemplateDetail detail = TemplateDetail.builder()
                        .name(req.getName())
                        .orderNumber(req.getOrderNumber())
                        .set(req.getSet())
                        .rep(req.getRep())
                        .time(req.getTime())
                        .build();

                template.addDetail(detail);
            }
        }
        Template updatedTemplate = templateRepository.save(template);
        return TemplateResponse.from(updatedTemplate);
    }


    //템플릿 삭제
    @Transactional
    public void deleteTemplate (Long templateId){
        User currentUser = userService.getCurrentUser();

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        //권한 확인
        if (!template.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this template");
        }

        templateRepository.delete(template);
    }


    //템플릿을 루틴으로 변환해주는 메서드  -> 템플릿을 RoutineDetailRequests로 보내 RoutineService에서 Dto를 받아 실행되도록 함

    public RoutineRequest templateToRoutine(Long templateId) {

        //템플릿 선택
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));

        //템플릿 상세 항목-> 루틴 항목으로 변환
        List<RoutineDetailRequest> detailRequests = new ArrayList<>();

        //TemplateDetail을 RoutineDetail로 변환
        for (TemplateDetail detail : template.getDetails()) {
            RoutineDetailRequest request = RoutineDetailRequest.builder()
                    .name(detail.getName())
                    .orderNumber(detail.getOrderNumber())
                    .set(detail.getSet())
                    .rep(detail.getRep())
                    .time(detail.getTime())
                    .build();
            //리스트에 저장
            detailRequests.add(request);
        }
        // orderNumber 기준으로 정렬
        Collections.sort(detailRequests, Comparator.comparing(RoutineDetailRequest::getOrderNumber));

        //RoutineResponse로 build
        RoutineRequest request = RoutineRequest.builder()
                .routineName(template.getPurposeName())
                .details(detailRequests)
                .build();

        return request;
        }
}
