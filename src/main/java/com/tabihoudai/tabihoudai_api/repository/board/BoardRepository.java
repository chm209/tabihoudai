package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    BoardEntity findByBoardIdx(@Param("boardIdx") Long boardIdx);

    @Query("SELECT B.boardIdx, B.title, B.regDate, U.nickname " +
            "FROM BoardEntity B " +
            "INNER JOIN UsersEntity U ON " +
            "U.userIdx = B.usersEntity.userIdx " +
            "ORDER BY B.regDate " +
            "LIMIT 5 ")
    List<Object[]> getRecentBoard();
}
