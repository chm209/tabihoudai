package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("SELECT B " +
            "FROM BoardEntity B " +
            "ORDER BY B.regDate ASC " +
            "LIMIT 5 ")
    List<BoardEntity> getBoard();

    @Transactional
    void deleteByBoardIdx(@Param("boardIdx") long boardIdx);
}
