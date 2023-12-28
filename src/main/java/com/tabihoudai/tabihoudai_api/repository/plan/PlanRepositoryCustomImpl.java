package com.tabihoudai.tabihoudai_api.repository.plan;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.plan.QPlanEntity.planEntity;
import static com.tabihoudai.tabihoudai_api.entity.users.QUsersEntity.usersEntity;
import static com.tabihoudai.tabihoudai_api.entity.plan.QPlanLikeEntity.planLikeEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Object[]> getAllPlan(Integer area) {
        List<Tuple> result;
        if (area == null) {
            result = queryFactory
                    .select(planEntity.planIdx,
                            planEntity.dateFrom,
                            planEntity.dateTo,
                            planEntity.title,
                            planEntity.attrList,
                            planLikeEntity.planLikeIdx.count(),
                            usersEntity.nickname)
                    .from(planEntity)
                    .leftJoin(planLikeEntity)
                    .on(planEntity.planIdx.eq(planLikeEntity.planEntity.planIdx))
                    .leftJoin(usersEntity)
                    .on(planEntity.usersEntity.userIdx.eq(usersEntity.userIdx))
                    .groupBy(planEntity.planIdx,
                            planEntity.dateFrom,
                            planEntity.dateTo,
                            planEntity.title,
                            planEntity.attrList,
                            usersEntity.nickname,
                            planLikeEntity.planEntity.planIdx)
                    .orderBy(planLikeEntity.planEntity.planIdx.count().desc())
                    .limit(5)
                    .fetch();
        } else {
            result = queryFactory
                    .select(planEntity.planIdx,
                            planEntity.dateFrom,
                            planEntity.dateTo,
                            planEntity.title,
                            planEntity.attrList,
                            planLikeEntity.planLikeIdx.count(),
                            usersEntity.nickname)
                    .from(planEntity)
                    .leftJoin(planLikeEntity)
                    .on(planEntity.planIdx.eq(planLikeEntity.planEntity.planIdx))
                    .leftJoin(usersEntity)
                    .on(planEntity.usersEntity.userIdx.eq(usersEntity.userIdx))
                    .where(planEntity.attrList.like(Integer.toString(area).concat("%")))
                    .groupBy(planEntity.planIdx,
                            planEntity.dateFrom,
                            planEntity.dateTo,
                            planEntity.title,
                            planEntity.attrList,
                            usersEntity.nickname,
                            planLikeEntity.planEntity.planIdx)
                    .orderBy(planLikeEntity.planEntity.planIdx.count().desc())
                    .limit(5)
                    .fetch();
        }
        return result.stream().map(Tuple::toArray).collect(Collectors.toList());
    }
}
