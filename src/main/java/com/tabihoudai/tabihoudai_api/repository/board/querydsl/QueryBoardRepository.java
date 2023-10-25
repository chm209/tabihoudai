package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import java.util.List;

public interface QueryBoardRepository {

    List<Object[]> getByBoardIdx(Long boardIdx);

    List<Object[]> getBoardList();
}
