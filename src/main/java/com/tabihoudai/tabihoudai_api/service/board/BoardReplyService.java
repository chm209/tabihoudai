package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardReplyService {
    List<BoardReplyDTO.getReplyDTO> getReply(Long boardIdx);

    void reportReply(BoardReplyDTO.reportReplyDTO dto);

    default BoardReplyDTO.getReplyDTO entityToGetDTO(BoardReplyEntity boardReply, UsersEntity users){
        return BoardReplyDTO.getReplyDTO.builder()
                .nickname(users.getNickname())
                .content(boardReply.getContent())
                .profile(users.getProfile())
                .regDate(boardReply.getRegDate())
                .build();
    }

    default BlameEntity dtoToEntityReport(BoardReplyEntity reply, UsersEntity users, String contents){
        return BlameEntity.builder()
                .usersEntity(users)
                .boardReplyEntity(reply)
                .category((byte)2)
                .content(contents)
                .build();
    }
}
