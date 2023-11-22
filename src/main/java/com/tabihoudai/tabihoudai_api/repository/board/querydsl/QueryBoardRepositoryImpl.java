package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    //카테고리별 게시글 리스트 조회, 전체 게시글 조회 가능
    @Override
    public Page<Object[]> getBoardList(Pageable pageable, Integer category) {
        List<Tuple> result = queryFactory.select(
                        boardEntity, boardEntity.usersEntity
                )
                .from(boardEntity)
                .leftJoin(boardEntity.usersEntity)
                .where(eqCategory(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(boardEntity.count())
                .from(boardEntity).where(eqCategory(category));

        return PageableExecutionUtils.getPage(
                result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList()), pageable,countQuery::fetchOne);
    }

    private BooleanExpression eqCategory(Integer category){
        if(category == null) return null;
        return boardEntity.category.eq(category);
    }

    //검색한 게시글 리스트
    @Override
    public Page<Object[]> searchBoard(Pageable pageable, String keyword, String type) {
        List<Tuple> result = queryFactory.select(
                        boardEntity, boardEntity.usersEntity
                )
                .from(boardEntity)
                .where(containsBoard(keyword, type))
                .leftJoin(boardEntity.usersEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(boardEntity.count())
                .from(boardEntity)
                .where(containsBoard(keyword, type));

        return PageableExecutionUtils.getPage(
                result.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList()), pageable, countQuery::fetchOne
        );
    }

    private BooleanExpression containsBoard(String keyword, String type){
        if(type.equals("0")) {//제목 기준으로 검색
            return boardEntity.title.contains(keyword);
        } else if (type.equals("1")) {//작성자 명으로 검색
            return boardEntity.usersEntity.nickname.contains(keyword);
        }
        return null;
    }

}
