package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardService {

    default BoardDTO entityToDTO(BoardEntity board, UsersEntity users, Long replyCount, Long likeCount){
        return BoardDTO.builder()
                .boardIdx(board.getBoardIdx())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .visitCount(board.getVisitCount())
                .nickname(users.getNickname())
                .replyCount(replyCount.intValue())
                .likeCount(likeCount.intValue())
                .build();
    }

    List<BoardDTO> get(long boardIdx);
}
