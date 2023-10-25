package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tabihoudai.tabihoudai_api.entity.board.QBoardEntity.boardEntity;
import static com.tabihoudai.tabihoudai_api.entity.board.QBoardLikeEntity.boardLikeEntity;
import static com.tabihoudai.tabihoudai_api.entity.board.QBoardReplyEntity.boardReplyEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QueryBoardRepositoryImpl implements QueryBoardRepository{

    private final JPAQueryFactory queryFactory;

    //게시글 상세보기
    @Override
    public List<Object[]> getByBoardIdx(Long boardIdx) {
        List<Tuple> result = queryFactory
                .select(
                        boardEntity,
                        boardEntity.usersEntity,
                        JPAExpressions.select(boardReplyEntity.count()).from(boardReplyEntity).where(boardReplyEntity.boardEntity.boardIdx.eq(boardIdx)),
                        JPAExpressions.select(boardLikeEntity.count()).from(boardLikeEntity).where(boardLikeEntity.boardEntity.boardIdx.eq(boardIdx))
                )
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .where(boardEntity.boardIdx.eq(boardIdx))
                .fetch();
        return result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
    }

    //게시글 리스트 보기
    @Override
    public List<Object[]> getBoardList() {
        List<Tuple> result = queryFactory
                .select(
                        boardEntity,
                        boardEntity.usersEntity
                )
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .fetch();
        return result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
    }
}
