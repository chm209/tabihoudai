package com.tabihoudai.tabihoudai_api.repository.admin;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.admin.QBlockEntity.blockEntity;
import static com.tabihoudai.tabihoudai_api.entity.admin.QCsEntity.csEntity;

public class CsRepositoryCustomImpl implements CsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    EntityManager em;

    public CsRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Object[]> getCsPage(Pageable pageable) {
        List<CsEntity> result = jpaQueryFactory
                .select(csEntity)
                .from(csEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBySort(pageable.getSort()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(csEntity.count())
                .from(csEntity);

        return PageableExecutionUtils.getPage(
                result.stream().map(csEntity -> new Object[]{
                        csEntity.getCsIdx(),
                        csEntity.getUsersEntity().getNickname(),
                        csEntity.getType(),
                        csEntity.getContent()}).collect(Collectors.toList()),
                pageable,
                countQuery::fetchOne
        );
    }

    @Override
    @Transactional
    public void patchCs(long csIdx, String reply) {
        jpaQueryFactory
                .update(csEntity)
                .set(csEntity.reply, reply)
                .set(csEntity.replyDate, LocalDate.now())
                .where(csEntity.csIdx.eq(csIdx))
                .execute();
        em.flush();
        em.clear();
    }

    private OrderSpecifier[] orderBySort(Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder pathBuilder = new PathBuilder(BannerEntity.class, "csEntity");
            return new OrderSpecifier(direction, pathBuilder.get(order.getProperty()));
        }).toArray(OrderSpecifier[]::new);
    }
}
