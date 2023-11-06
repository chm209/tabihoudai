package com.tabihoudai.tabihoudai_api.repository.admin;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.admin.QBannerEntity.bannerEntity;

@Repository
@Slf4j
public class BannerRepositoryCustomImpl implements BannerRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BannerRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Object[]> getBannerPage(Pageable pageable) {

        List<BannerEntity> bannerEntityList = jpaQueryFactory
                .select(bannerEntity)
                .from(bannerEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBySort(pageable.getSort()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(bannerEntity.count())
                .from(bannerEntity);

        return PageableExecutionUtils.getPage(
                bannerEntityList.stream().map(bannerEntity1 -> new Object[] {bannerEntity1.getBannerIdx(), bannerEntity1.getPath(), bannerEntity1.getRegDate()}).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
                );
    }

    private OrderSpecifier[] orderBySort(Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(BannerEntity.class, "bannerEntity");
            return new OrderSpecifier(direction, pathBuilder.get(order.getProperty()));
        }).toArray(OrderSpecifier[]::new);
    }
}
