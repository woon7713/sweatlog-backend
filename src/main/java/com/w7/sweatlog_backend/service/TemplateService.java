package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.RoutineDetailRequest;
import com.w7.sweatlog_backend.entity.Routine;
import com.w7.sweatlog_backend.entity.Template;
import com.w7.sweatlog_backend.entity.TemplateDetail;
import com.w7.sweatlog_backend.exception.ResourceNotFoundException;
import com.w7.sweatlog_backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {

    private final  TemplateRepository templateRepository;

    //템플릿 전체 출력
    public List<Template> findAllTemplates() {
        DefaultTemplates();
        return templateRepository.findAll();
    }

    //템플릿을 루틴으로 변환해주는 메서드  -> 템플릿을 RoutineDetailRequests로 보내 RoutineService에서 Dto를 받아 실행되도록 함

    public List<RoutineDetailRequest> templateToRoutine(Long templateId) {

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));


        //TemplateDetail의 항목들을 RequestDetailRequest Dto로 만드는 과정
        List<RoutineDetailRequest> routineDetailRequests = new ArrayList<>();

        for (TemplateDetail detail : template.getDetails()) {
            RoutineDetailRequest request = RoutineDetailRequest.builder()
                    .name(detail.getName())
                    .orderNumber(detail.getOrderNumber())
                    .set(detail.getSet())
                    .rep(detail.getRep())
                    .time(detail.getTime())
                    .build();
            //리스트에 저장
            routineDetailRequests.add(request);
        }

            // orderNumber 기준으로 정렬하여 순서를 보장
            Collections.sort(routineDetailRequests, (r1, r2) -> Integer.compare(r1.getOrderNumber(), r2.getOrderNumber()));

            return routineDetailRequests;
        }


        //기본 템플릿
    public void DefaultTemplates() {

        //데이터가 있으면 초기화 하지않음
        if (templateRepository.count() > 0) {
            return;
        }
        List<Template> templates = new ArrayList<>();

        // 1. 초보자 전신 근력 루틴 템플릿 (목적을 name에 포함)
        Template beginnerFullBody = Template.builder()
                .purposeName("근력 향상 ") // 목적(근력 향상)을 이름에 포함
                .build();

        Arrays.asList(
                TemplateDetail.builder().name("스쿼트").orderNumber(1).set(3).rep(12).time(null).build(),
                TemplateDetail.builder().name("벤치 프레스").orderNumber(2).set(3).rep(10).time(null).build(),
                TemplateDetail.builder().name("데드리프트").orderNumber(3).set(3).rep(8).time(null).build(),
                TemplateDetail.builder().name("밀리터리 프레스").orderNumber(4).set(3).rep(10).time(null).build(),
                TemplateDetail.builder().name("바벨 로우").orderNumber(5).set(3).rep(12).time(null).build()
        ).forEach(beginnerFullBody::addDetail);
        templates.add(beginnerFullBody);


        // 2. 지구력 & 유산소 루틴 템플릿 (목적을 name에 포함)
        Template enduranceCardio = Template.builder()
                .purposeName(" 지구력 & 유산소") // 목적(지방 연소)을 이름에 포함
                // .purpose 필드 제거
                .build();

        Arrays.asList(
                // Time 필드를 사용
                TemplateDetail.builder().name("러닝 (중강도)").orderNumber(1).set(null).rep(null).time(30).build(),
                TemplateDetail.builder().name("버피 테스트").orderNumber(2).set(3).rep(15).time(null).build(),
                TemplateDetail.builder().name("플랭크").orderNumber(3).set(5).rep(null).time(1).build(), // 5세트, 각 1분
                TemplateDetail.builder().name("사이클 (저강도)").orderNumber(4).set(null).rep(null).time(45).build()
        ).forEach(enduranceCardio::addDetail);
        templates.add(enduranceCardio);

        // 3. 상급자 분할 운동 루틴 템플릿 (가슴/삼두) (목적을 name에 포함)
        Template advancedSplit = Template.builder()
                .purposeName(" 상급자 분할: 가슴/삼두") // 목적(근비대)를 이름에 포함
                .build();

        Arrays.asList(
                TemplateDetail.builder().name("인클라인 덤벨 프레스").orderNumber(1).set(4).rep(8).time(null).build(),
                TemplateDetail.builder().name("펙 덱 플라이").orderNumber(2).set(3).rep(15).time(null).build(),
                TemplateDetail.builder().name("딥스 (가슴 타겟)").orderNumber(3).set(3).rep(10).time(null).build(),
                TemplateDetail.builder().name("케이블 푸시 다운").orderNumber(4).set(4).rep(12).time(null).build(),
                TemplateDetail.builder().name("오버헤드 트라이셉스 익스텐션").orderNumber(5).set(3).rep(10).time(null).build()
        ).forEach(advancedSplit::addDetail);
        templates.add(advancedSplit);

        templateRepository.saveAll(templates);
    }

}
