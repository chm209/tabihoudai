package com.tabihoudai.tabihoudai_api.repository.admin.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.tabihoudai.tabihoudai_api.entity.admin.QBlameEntity.blameEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QueryBlameRepositoryImpl implements QueryBlameRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteReplyBlame(Long replyIdx) {
        queryFactory.delete(blameEntity)
                .where(blameEntity.boardReplyEntity.boardReplyIdx.eq(replyIdx))
                .execute();
    }
}
