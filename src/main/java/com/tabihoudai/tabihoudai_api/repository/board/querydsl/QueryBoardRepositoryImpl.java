package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
        queryFactory.update(boardEntity)
                .set(boardEntity.visitCount, boardEntity.visitCount.add(1))
                .where(boardEntity.boardIdx.eq(boardIdx))
                .execute();
        List<Tuple> result = queryFactory
                .select(
                        boardEntity,
                        boardEntity.usersEntity,
                        JPAExpressions.select(boardReplyEntity.count()).from(boardReplyEntity).where(boardReplyEntity.boardEntity.boardIdx.eq(boardIdx)),
                        JPAExpressions.select(boardLikeEntity.count()).from(boardLikeEntity).where(boardLikeEntity.boardEntity.boardIdx.eq(boardIdx).and(boardLikeEntity.flag.eq((byte) 1))),
                        JPAExpressions.select(boardLikeEntity.count()).from(boardLikeEntity).where(boardLikeEntity.boardEntity.boardIdx.eq(boardIdx).and(boardLikeEntity.flag.eq((byte) 2)))
                )
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .where(boardEntity.boardIdx.eq(boardIdx))
                .fetch();
        return result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getBoardByCategory(int category) {
        List<Tuple> result = queryFactory.select(boardEntity, boardEntity.usersEntity)
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .where(boardEntity.category.eq(category))
                .fetch();
        return result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
    }

    @Override
    public Page<Object[]> getBoardList(Pageable pageable, int category) {
        List<Tuple> result = queryFactory.select(
                        boardEntity, boardEntity.usersEntity
                )
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .where(boardEntity.category.eq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(boardEntity.count())
                .from(boardEntity);

        return PageableExecutionUtils.getPage(
                result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList()), pageable,countQuery::fetchOne);
    }

}
