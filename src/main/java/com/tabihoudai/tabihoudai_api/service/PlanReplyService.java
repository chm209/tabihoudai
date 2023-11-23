package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.PlanReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlanReplyService {

    private final PlanReplyRepository planReplyRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Long create(PlanReplyDTO planReplyDTO) {
        UsersEntity usersEntity = usersRepository.findByUserIdx(planReplyDTO.getUserIdx());
        PlanReplyEntity planReplyEntity = planReplyDTO.planReplyDtoToEntity();
        planReplyEntity.setUsersEntity(usersEntity);
        return planReplyRepository.save(planReplyEntity).getPlanReplyIdx();
    }
}
