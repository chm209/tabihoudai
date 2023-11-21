package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryBoardRepository {

    List<Object[]> getByBoardIdx(Long boardIdx);

    List<Object[]> getBoardByCategory(int category);

    Page<Object[]> getBoardList(Pageable pageable, Integer category);

    Page<Object[]> getListNullCategory(Pageable pageable);
}
