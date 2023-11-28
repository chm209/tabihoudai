package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.repository.board.querydsl.QueryBoardReplyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long> , QueryBoardReplyRepository {
    BoardReplyEntity findByBoardReplyIdx(long BoardReplyIdx);

}