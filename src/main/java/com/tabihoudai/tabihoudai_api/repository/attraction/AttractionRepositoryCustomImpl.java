package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.querydsl.core.Tuple;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionImageEntity.attractionImageEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QRegionEntity.regionEntity;

@Slf4j
public class AttractionRepositoryCustomImpl implements AttractionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
	EntityManager em;

    public AttractionRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    public void patchModifyAttraction(AttractionEntity attrEntity) {
        jpaQueryFactory
                .update(attractionEntity)
                .set(attractionEntity.attrIdx, attrEntity.getAttrIdx())
                .set(attractionEntity.address, attrEntity.getAddress())
                .set(attractionEntity.description, attrEntity.getDescription())
                .set(attractionEntity.latitude, attrEntity.getLatitude())
                .set(attractionEntity.longitude, attrEntity.getLongitude())
                .set(attractionEntity.attraction, attrEntity.getAttraction())
                .set(attractionEntity.tag, attrEntity.getTag())
                .set(attractionEntity.regionEntity, attrEntity.getRegionEntity())
                .where(attractionEntity.attrIdx.eq(attrEntity.getAttrIdx()))
                .execute();
        em.flush();
        em.clear();
    }

    @Transactional
    public void patchModifyAttrImg(AttractionImageEntity originalImgEntity, Long imgOffset, Long newIdx) {
        jpaQueryFactory
                .update(attractionImageEntity)
                .set(attractionImageEntity.attrImgIdx, newIdx)
                .where(attractionImageEntity.attrEntity.attrIdx.eq(imgOffset))
                .where(attractionImageEntity.attrImgIdx.eq(originalImgEntity.getAttrImgIdx()))
                .execute();
        em.flush();
        em.clear();
    }

    @Transactional
    public void patchModifyAttrMainImg(String newThumbnails, Long offsetIdx) {
        jpaQueryFactory
                .update(attractionImageEntity)
                .set(attractionImageEntity.type, '0')
                .where(attractionImageEntity.attrImgIdx.eq(offsetIdx))
                .execute();
        em.flush();

        AttractionImageEntity attrEntity = jpaQueryFactory
                .select(attractionImageEntity)
                .from(attractionImageEntity)
                .where(attractionImageEntity.attrImgIdx.eq(offsetIdx))
                .fetchOne();

        if(attrEntity.getPath().equals(newThumbnails)) {
            jpaQueryFactory
                    .update(attractionImageEntity)
                    .set(attractionImageEntity.type, '1')
                    .where(attractionImageEntity.attrImgIdx.eq(offsetIdx))
                    .execute();
            em.flush();
        }
        em.clear();
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