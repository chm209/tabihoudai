package com.tabihoudai.tabihoudai_api.application.plan;

import com.tabihoudai.tabihoudai_api.dao.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.plan.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public PlanDTO planView(Long planIdx) {
        List<PlanEntity> plan = planRepository.planView(planIdx);
        PlanEntity planEntity = plan.get(0);
        UsersEntity usersEntity = planEntity.getUsersEntity();
        return planRepository.planEntityToDTO(planEntity, usersEntity);
    }

}
