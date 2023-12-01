package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import com.tabihoudai.tabihoudai_api.entity.board.BoardLikeEntity;

import java.util.Optional;
import java.util.UUID;

public interface QueryBoardLikeRepository {
    void deleteLikeByBoardIdx(Long boardIdx);

    Optional<BoardLikeEntity> findByBoardAndUsers(Long boardIdx, UUID userIdx);
}
