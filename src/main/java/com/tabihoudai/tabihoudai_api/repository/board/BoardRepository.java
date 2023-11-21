package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query("SELECT new map(b.regDate as regDate, b.title as content, b.boardIdx as id) FROM BoardEntity b WHERE b.usersEntity.userIdx = :userIdx AND ROWNUM <= 4 ORDER BY id DESC")
    List<Map<String, Object>> findByUserIdx(@Param("userIdx") UUID userIdx);


}
