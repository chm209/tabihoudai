package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.PlanEditDTO;
import com.tabihoudai.tabihoudai_api.dto.PlanPagingDTO;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;


    @Transactional
    public Long create(PlanDTO planDTO) {
        UsersEntity usersEntity = usersRepository.findByUserIdx(planDTO.getUserIdx());
        PlanEntity planEntity = planDTO.planDtoToEntity();
        planEntity.setUsersEntity(usersEntity);
        return planRepository.save(planEntity).getPlanIdx();
    }

    @Transactional
    public PlanDTO planView(Long planIdx) {
        List<PlanEntity> plan = planRepository.planView(planIdx);
        PlanEntity planEntity = plan.get(0);
        UsersEntity usersEntity = planEntity.getUsersEntity();
        planRepository.addVisitCount(planIdx);
        return PlanDTO.planEntityToDTO(planEntity, usersEntity);
    }

    @Transactional
    public Long edit(PlanEditDTO planEditDTO) {
        PlanEntity planEntity = planEditDTO.planEditDtoToEntity();
        planRepository.editPlan(planEntity);
        return planEditDTO.getPlanIdx();
    }

    public Page<PlanPagingDTO> orderedPlanList(Pageable pageable) {
        Page<Object[]> page = planRepository.orderedPlanList(pageable);
        return page.map(data -> PlanPagingDTO.arrayToDTO(data));
    }

    public Page<PlanPagingDTO> searchPlan(Pageable pageable, String keyword){
        Page<Object[]> page = planRepository.planSearch(pageable, keyword);
        return page.map(data -> PlanPagingDTO.arrayToDTO(data));
    }

}
