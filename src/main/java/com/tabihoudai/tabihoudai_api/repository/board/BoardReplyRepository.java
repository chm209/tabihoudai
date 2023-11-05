package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long> {
    BoardReplyEntity findByBoardReplyIdx(@Param("boardReplyIdx") Long boardReplyIdx);

    @Transactional
    void deleteByBoardReplyIdx(@Param("boardReplyIdx") long boardReplyIdx);

    @Transactional
    void deleteByBoardEntity_BoardIdx(@Param("boardIdx") long boardIdx);
}
