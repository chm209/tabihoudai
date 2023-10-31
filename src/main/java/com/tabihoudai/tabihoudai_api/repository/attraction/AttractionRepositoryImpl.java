package com.tabihoudai.tabihoudai_api.repository.attraction;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrDetailDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrImgDto;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.avg;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity.attrReplyEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrImgEntity.attrImgEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QRegionEntity.regionEntity;
@Repository
@RequiredArgsConstructor
@Slf4j
public class AttractionRepositoryImpl implements AttractionRepository{
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Object[]> getAttractionList(Pageable pageable,String area,String word) {
        JPAQuery<Long> count=null;
        List<Tuple> result = null;
        if (area.equals("city")){

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
                    .where(attractionEntity.regionIdx.city.eq(word))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            count = queryFactory
                    .select(attractionEntity.count())
                    .from(attractionEntity)
                    .where(attractionEntity.regionIdx.city.eq(word));

        } else if (area.equals("area")) {

            result = queryFactory
                    .select(attractionEntity,
                            attrImgEntity,
                            select(attrReplyEntity.score.avg().coalesce(0.0))
                                    .from(attrReplyEntity)
                                    .where(attrReplyEntity.attrIdx.eq(attractionEntity)),
                            select(attrReplyEntity.count())
                                    .from(attrReplyEntity)
                                    .where(attrReplyEntity.attrIdx.eq(attractionEntity))
                    )
                    .from(attractionEntity)
                    .leftJoin(attrImgEntity).on(attrImgEntity.attrIdx.eq(attractionEntity).and(attrImgEntity.type.eq('0')))
                    .where(attractionEntity.regionIdx.area.eq(word))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            count = queryFactory
                    .select(attractionEntity.count())
                    .from(attractionEntity)
                    .where(attractionEntity.regionIdx.area.eq(word));

        } else if (area.equals("attraction")) {

            result = queryFactory
                    .select(attractionEntity,
                            attrImgEntity,
                            select(attrReplyEntity.score.avg().coalesce(0.0))
                                    .from(attrReplyEntity)
                                    .where(attrReplyEntity.attrIdx.eq(attractionEntity)),
                            select(attrReplyEntity.count())
                                    .from(attrReplyEntity)
                                    .where(attrReplyEntity.attrIdx.eq(attractionEntity))
                    )
                    .from(attractionEntity)
                    .leftJoin(attrImgEntity).on(attrImgEntity.attrIdx.eq(attractionEntity).and(attrImgEntity.type.eq('0')))
                    .where(attractionEntity.attraction.eq(word))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            count = queryFactory
                    .select(attractionEntity.count())
                    .from(attractionEntity)
                    .where(attractionEntity.attraction.eq(word));

        }

        return PageableExecutionUtils.getPage(result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,count::fetchOne);
    }

    @Override
    public List<AttractionEntity> getAttractionDetail(Long attrIdx) {

        List<AttractionEntity> result = queryFactory.select(attractionEntity
                        )
                .from(attractionEntity)
                .where(attractionEntity.attrIdx.eq(attrIdx))
                .fetch();

        return result;
    }

    @Override
    public List<AttrReplyEntity> getAttractionReply(Long attrIdx){

        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();

        List<AttrReplyEntity> result = queryFactory.select(attrReplyEntity)
                .from(attrReplyEntity)
                .where(attrReplyEntity.attrIdx.eq(attraction))
                .fetch();

        return result;
    }

    @Override
    public List<Double> getAttractionAvg(Long attrIdx){
        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();
        List<Double> result =
                queryFactory.select(attrReplyEntity.score.avg().coalesce(0.0))
                .from(attrReplyEntity)
                .where(attrReplyEntity.attrIdx.eq(attraction))
                .fetch();

        return result;
    }

    @Override
    public List<AttrImgEntity> getAttractionImg(Long attrIdx){
        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();

        List<AttrImgEntity> result = queryFactory.select(attrImgEntity)
                .from(attrImgEntity)
                .where(attrImgEntity.attrIdx.eq(attraction))
                .fetch();

        return result;
    }


}
