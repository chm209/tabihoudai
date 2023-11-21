package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long> {

    @Query("SELECT new map(p.regDate as regDate, p.content as content, p.boardReplyIdx as id) FROM BoardReplyEntity p WHERE p.usersEntity.userIdx = :userIdx AND ROWNUM <= 4 ORDER BY id DESC")
    List<Map<String, Object>> findByUserIdx(@Param("userIdx") UUID userIdx);

}
