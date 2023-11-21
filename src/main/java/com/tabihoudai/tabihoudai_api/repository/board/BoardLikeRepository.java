package com.tabihoudai.tabihoudai_api.repository.board;

import com.tabihoudai.tabihoudai_api.entity.board.BoardLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
}
