package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.PlanLikeDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanLikeRepository;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PlanLikeService {

    private final PlanLikeRepository planLikeRepository;
    private final UsersRepository usersRepository;


    public Long chkLike(PlanLikeDTO planLikeDTO) {
        PlanLikeEntity planLikeEntity = planLikeDTO.planLikeDtoToEntity();
        return planLikeRepository.addLike(planLikeEntity);
    }

    public void addLike(PlanLikeDTO planLikeDTO) {
        UsersEntity usersEntity = usersRepository.findByUserIdx(planLikeDTO.getUserIdx());
        PlanLikeEntity planLikeEntity = planLikeDTO.planLikeDtoToEntity();
        planLikeEntity.setUsersEntity(usersEntity);
        try {
            if(planLikeRepository.chkLiked(planLikeEntity).size() == 0) {
                planLikeRepository.save(planLikeEntity);
            } else {
                throw new Exception();
            }

        } catch(Exception e) {

        }

    }

    @Transactional
    public void disLike(PlanLikeDTO planLikeDTO) {
        PlanLikeEntity planLikeEntity = planLikeDTO.planLikeDtoToEntity();
        planLikeRepository.disLike(planLikeEntity);
    }

}
