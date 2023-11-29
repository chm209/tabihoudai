package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.tabihoudai.tabihoudai_api.entity.board.QBoardLikeEntity.boardLikeEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QueryBoardLikeRepositoryImpl implements QueryBoardLikeRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteLikeByBoardIdx(Long boardIdx) {
        queryFactory.delete(boardLikeEntity)
                .where(boardLikeEntity.boardEntity.boardIdx.eq(boardIdx))
                .execute();
    }
}
