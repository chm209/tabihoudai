package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QRegionEntity.regionEntity;

public class AttractionRepositoryCustomImpl implements AttractionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public AttractionRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

     public Page<Object[]> getAttractionPage(String area, String city, Pageable pageable) {

        List<Tuple> result = jpaQueryFactory
                .select(attractionEntity.attrIdx,
                        attractionEntity.address,
                        attractionEntity.attraction,
                        regionEntity.area,
                        regionEntity.city)
                .from(attractionEntity)
                .leftJoin(regionEntity).on(regionEntity.area.eq(area))
                .where(regionEntity.regionIdx.eq(attractionEntity.regionEntity.regionIdx))
                .where(regionEntity.city.eq(city))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBySort(pageable.getSort()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(attractionEntity.count())
                .from(attractionEntity)
                .leftJoin(regionEntity).on(regionEntity.area.eq(area))
                .where(regionEntity.regionIdx.eq(attractionEntity.regionEntity.regionIdx))
                .where(regionEntity.city.eq(city));

        return PageableExecutionUtils.getPage(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne);
    }

    private OrderSpecifier[] orderBySort(Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(AttractionEntity.class, "attractionEntity");
            return new OrderSpecifier(direction, pathBuilder.get(order.getProperty()));
        }).toArray(OrderSpecifier[]::new);
    }
}