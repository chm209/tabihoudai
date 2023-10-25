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
    public List<Object[]> getBestPlan() {
        List<Tuple> result = queryFactory
                .select(planEntity.planIdx,
                        planEntity.dateFrom,
                        planEntity.dateTo,
                        planEntity.title,
                        planEntity.attrList,
                        usersEntity.nickname)
                .from(planEntity)
                .leftJoin(planLikeEntity)
                .on(planEntity.planIdx.eq(planLikeEntity.planLikeIdx))
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
        log.info("{}", result.stream().toList());
        return result.stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getAreaBestPlan(Integer area) {
        List<Tuple> result = queryFactory
                .select(planEntity.planIdx,
                        planEntity.dateFrom,
                        planEntity.dateTo,
                        planEntity.title,
                        planEntity.attrList,
                        usersEntity.nickname)
                .from(planEntity)
                .leftJoin(planLikeEntity)
                .on(planEntity.planIdx.eq(planLikeEntity.planLikeIdx))
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
        log.info("{}", result.stream().toList());
        return result.stream().map(Tuple::toArray).collect(Collectors.toList());
    }
}
