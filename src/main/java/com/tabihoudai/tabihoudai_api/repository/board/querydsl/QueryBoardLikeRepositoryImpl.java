package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tabihoudai.tabihoudai_api.entity.board.BoardLikeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<BoardLikeEntity> findByBoardAndUsers(Long boardIdx, UUID userIdx) {
        BoardLikeEntity result = queryFactory.select(boardLikeEntity)
                .from(boardLikeEntity)
                .where(boardLikeEntity.boardEntity.boardIdx.eq(boardIdx),
                        boardLikeEntity.usersEntity.userIdx.eq(userIdx))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
