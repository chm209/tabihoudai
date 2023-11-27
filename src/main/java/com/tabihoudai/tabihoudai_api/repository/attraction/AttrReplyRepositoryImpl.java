package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity.attrReplyEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrImgEntity.attrImgEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QRegionEntity.regionEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AttrReplyRepositoryImpl implements AttrReplyRepository{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<AttrReplyEntity> getAttractionReply(Long attrIdx) {

        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();

        List<AttrReplyEntity> result = queryFactory.select(attrReplyEntity)
                .from(attrReplyEntity)
                .where(attrReplyEntity.attrIdx.eq(attraction))
                .fetch();

        return result;
    }

    @Override
    public List<Double> getAttractionAvg(Long attrIdx) {
        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();
        List<Double> result =
                queryFactory.select(attrReplyEntity.score.avg().coalesce(0.0))
                        .from(attrReplyEntity)
                        .where(attrReplyEntity.attrIdx.eq(attraction))
                        .fetch();

        return result;
    }



}
