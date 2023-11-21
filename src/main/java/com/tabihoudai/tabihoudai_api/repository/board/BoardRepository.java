package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.repository.board.querydsl.QueryBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>, QueryBoardRepository {
    BoardEntity findByBoardIdx(long boardIdx);
}
