package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.board.QBoardReplyEntity.boardReplyEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QueryBoardReplyRepositoryImpl implements QueryBoardReplyRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Object[]> getReplyByBoardIdx(Long BoardIdx) {
        List<Tuple> result = queryFactory.select(boardReplyEntity, boardReplyEntity.usersEntity)
                .from(boardReplyEntity)
                .where(boardReplyEntity.boardEntity.boardIdx.eq(BoardIdx))
                .orderBy(boardReplyEntity.boardReplyIdx.desc())
                .fetch();
        return result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
    }

    @Override
    public void removeReplyByBoardIdx(Long BoardIdx) {
        queryFactory.delete(boardReplyEntity)
                .where(boardReplyEntity.boardEntity.boardIdx.eq(BoardIdx))
                .execute();
    }
}
