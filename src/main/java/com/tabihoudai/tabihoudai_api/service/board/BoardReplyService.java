package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardReplyService {
    List<BoardReplyDTO.getReplyDTO> getReply(Long boardIdx);

    default BoardReplyDTO.getReplyDTO entityToGetDTO(BoardReplyEntity boardReply, UsersEntity users){
        return BoardReplyDTO.getReplyDTO.builder()
                .nickname(users.getNickname())
                .content(boardReply.getContent())
                .profile(users.getProfile())
                .regDate(boardReply.getRegDate())
                .build();
    }
}
