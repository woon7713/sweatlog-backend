package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.dto.*;
import com.w7.sweatlog_backend.entity.Routine;
import com.w7.sweatlog_backend.entity.RoutineDetail;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.exception.ResourceNotFoundException;
import com.w7.sweatlog_backend.exception.UnauthorizedException;
import com.w7.sweatlog_backend.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserService userService;

    public RoutineResponse createRoutine(RoutineRequest request) {

        User currentUser = userService.getCurrentUser();

        Routine routine = Routine.builder()
                .user(currentUser)
                .routineName(request.getRoutineName())
                .build();

        // RoutineDetail 생성
        List<RoutineDetailRequest> detailRequests = request.getDetails();

        //RoutineDetail의 값이 있으면 반복해서 detail값 생성
        if (detailRequests != null) {
            for (RoutineDetailRequest req : detailRequests) {
                RoutineDetail detail = RoutineDetail.builder()
                        .name(req.getName())
                        .orderNumber(req.getOrderNumber())
                        .set(req.getSet())
                        .rep(req.getRep())
                        .time(req.getTime())
                        .build();

                //리스트에 추가
                routine.addDetail(detail);
            }
        }

                Routine updatedRoutine = routineRepository.save(routine);
                return RoutineResponse.from(updatedRoutine);
    }


    //나의 루틴이니까 내 루틴만 볼수 있게 설정
    @Transactional(readOnly = true)
    public Page<RoutineResponse> getMyRoutines(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Page<Routine> routines = routineRepository.findByUser(currentUser, pageable);
        return routines.map(RoutineResponse::from);
    }


    //루틴 수정
    public RoutineResponse updateRoutine(Long routineId, RoutineRequest request) {
        User currentUser = userService.getCurrentUser();

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found"));

        // RoutineDetail 초기화
        routine.getDetails().clear();

        routine.setRoutineName(request.getRoutineName());

        // 새로운 RoutineDetail 생성 및 수정
        List<RoutineDetailRequest> detailRequests = request.getDetails();

        if (detailRequests != null) {
            for (RoutineDetailRequest req : detailRequests) {
                RoutineDetail detail = RoutineDetail.builder()
                        .name(req.getName())
                        .orderNumber(req.getOrderNumber())
                        .set(req.getSet())
                        .rep(req.getRep())
                        .time(req.getTime())
                        .build();

                routine.addDetail(detail);
            }
        }
        Routine updatedRoutine = routineRepository.save(routine);
        return RoutineResponse.from(updatedRoutine);
    }


            //루틴 삭제
            @Transactional
            public void deleteRoutine (Long routineId){
                User currentUser = userService.getCurrentUser();

                Routine routine = routineRepository.findById(routineId)
                        .orElseThrow(() -> new ResourceNotFoundException("Routine not found"));
                //권한 확인
                if (!routine.getUser().getId().equals(currentUser.getId())) {
                    throw new UnauthorizedException("You are not authorized to delete this routine");
                }

                routineRepository.delete(routine);
            }
        }
