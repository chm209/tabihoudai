package com.tabihoudai.tabihoudai_api.repository.attraction;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.attraction.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity.attrReplyEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrImgEntity.attrImgEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QRegionEntity.regionEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AttractionRepositoryImpl implements AttractionRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Object[]> getAttractionList(Pageable pageable, String area,String city, String attr, String word) {
        JPAQuery<Long> count = null;
        List<Tuple> result = null;

        result = queryFactory
                .select(attractionEntity,
                        attrImgEntity,
                        JPAExpressions.select(attrReplyEntity.score.avg().coalesce(0.0))
                                .from(attrReplyEntity)
                                .where(attrReplyEntity.attrIdx.eq(attractionEntity)),
                        JPAExpressions.select(attrReplyEntity.count())
                                .from(attrReplyEntity)
                                .where(attrReplyEntity.attrIdx.eq(attractionEntity))
                )
                .from(attractionEntity)
                .leftJoin(attrImgEntity).on(attrImgEntity.attrIdx.eq(attractionEntity).and(attrImgEntity.type.eq('0')))
                .where(cityEq(city,word),areaEq(area,word),attrEq(attr,word))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(attrSort(pageable))
                .fetch();

        count = queryFactory
                .select(attractionEntity.count())
                .from(attractionEntity)
                .where(cityEq(city,word),areaEq(area,word),attrEq(attr,word));


        return PageableExecutionUtils.getPage(result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable, count::fetchOne);
    }

    private BooleanExpression cityEq(String city, String word) {
        return city != null ? attractionEntity.regionIdx.city.eq(word) : null;
    }

    private BooleanExpression areaEq(String area, String word) {
        return area != null ? attractionEntity.regionIdx.area.eq(word) : null;
    }

    private BooleanExpression attrEq(String attr, String word) {
        return attr != null ? attractionEntity.attraction.eq(word) : null;
    }

    private OrderSpecifier<?> attrSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                switch (order.getProperty()) {
                    case "grade" -> {
                        return new OrderSpecifier(Order.DESC, JPAExpressions.select(attrReplyEntity.score.avg().coalesce(0.0))
                                .from(attrReplyEntity)
                                .where(attrReplyEntity.attrIdx.eq(attractionEntity)));
                    }
                    case "attraction" -> {
                        return new OrderSpecifier(Order.ASC, attractionEntity.attraction);
                    }
                    case "commentCount" -> {
                        return new OrderSpecifier(Order.DESC, JPAExpressions.select(attrReplyEntity.count())
                                .from(attrReplyEntity)
                                .where(attrReplyEntity.attrIdx.eq(attractionEntity)));
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<AttractionEntity> getAttractionDetail(Long attrIdx) {

        List<AttractionEntity> result = queryFactory.select(attractionEntity)
                .from(attractionEntity)
                .where(attractionEntity.attrIdx.eq(attrIdx))
                .fetch();

        return result;
    }

    @Override
    public List<AttrImgEntity> getAttractionImg(Long attrIdx) {
        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();

        List<AttrImgEntity> result = queryFactory.select(attrImgEntity)
                .from(attrImgEntity)
                .where(attrImgEntity.attrIdx.eq(attraction))
                .fetch();

        return result;
    }

    @Override
    public List<RegionEntity> getRegion() {

        List<RegionEntity> result = queryFactory.select(regionEntity)
                .from(regionEntity)
                .fetch();

        return result;
    }

    @Override
    public AttractionEntity getAttraction(Long attrIdx) {

        return queryFactory.selectFrom(attractionEntity).where(attractionEntity.attrIdx.eq(attrIdx)).fetchOne();
    }


}
