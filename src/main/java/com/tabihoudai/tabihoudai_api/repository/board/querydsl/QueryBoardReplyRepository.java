package com.tabihoudai.tabihoudai_api.repository.board.querydsl;

import java.util.List;

public interface QueryBoardReplyRepository {
    List<Object[]> getReplyByBoardIdx(Long BoardIdx);
}
