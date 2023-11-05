package com.tabihoudai.tabihoudai_api.repository.users;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import static com.tabihoudai.tabihoudai_api.entity.users.QUsersEntity.usersEntity;

public class UsersRepositoryCustomImpl implements UsersRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    EntityManager em;

    public UsersRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    public void patchUsersBlockCondition(UsersEntity user) {
        jpaQueryFactory
                .update(usersEntity)
                .set(usersEntity.block, (byte) 1)
                .where(usersEntity.userIdx.eq(user.getUserIdx()))
                .execute();
        em.flush();
        em.clear();
    }
}
