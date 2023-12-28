package com.tabihoudai.tabihoudai_api.repository.admin;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlockEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.admin.QBlameEntity.blameEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionImageEntity.attractionImageEntity;

@Repository
@Slf4j
public class BlameRepositoryCustomImpl implements BlameRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager em;
    public BlameRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Object[]> getBlamePage(Pageable pageable) {

        List<BlameEntity> result = jpaQueryFactory
                .select(blameEntity)
                .from(blameEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBySort(pageable.getSort()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(blameEntity.count())
                .from(blameEntity);

        return PageableExecutionUtils.getPage(
                result.stream().map(blameEntity -> new Object[] {
                        blameEntity.getBlameIdx(),
                        blameEntity.getCategory(),
                        blameEntity.getContent()}).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
                );
    }

    private OrderSpecifier[] orderBySort(Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(BannerEntity.class, "blameEntity");
            return new OrderSpecifier(direction, pathBuilder.get(order.getProperty()));
        }).toArray(OrderSpecifier[]::new);
    }
}
