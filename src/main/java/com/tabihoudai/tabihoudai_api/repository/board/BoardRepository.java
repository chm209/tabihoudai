package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
