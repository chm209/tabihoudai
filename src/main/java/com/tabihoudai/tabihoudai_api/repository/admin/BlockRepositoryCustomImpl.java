package com.tabihoudai.tabihoudai_api.repository.admin;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.admin.BlockEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import static com.tabihoudai.tabihoudai_api.entity.admin.QBlockEntity.blockEntity;

public class BlockRepositoryCustomImpl implements BlockRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager em;

    public BlockRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    @Override
    public void patchBlockManager(BlockEntity block) {
        jpaQueryFactory
                .update(blockEntity)
                .set(blockEntity.count, block.getCount())
                .set(blockEntity.endDate, block.getEndDate())
                .where(blockEntity.usersEntity.userIdx.eq(block.getUsersEntity().getUserIdx()))
                .execute();
        em.flush();
        em.clear();
    }
}
