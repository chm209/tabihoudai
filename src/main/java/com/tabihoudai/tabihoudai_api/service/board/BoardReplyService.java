package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardReplyService {
    List<BoardReplyDTO.getReplyDTO> getReply(Long boardIdx);

    void registerReply(BoardReplyDTO.replyRegisterDTO dto);

    void reportReply(BoardReplyDTO.reportReplyDTO dto);

    void removeReply(Long replyIdx);

    default BoardReplyDTO.getReplyDTO entityToGetDTO(BoardReplyEntity boardReply, UsersEntity users){
        return BoardReplyDTO.getReplyDTO.builder()
                .nickname(users.getNickname())
                .content(boardReply.getContent())
                .profile(users.getProfile())
                .regDate(boardReply.getRegDate())
                .build();
    }

    default BoardReplyEntity dtoToEntityReply(BoardEntity board, UsersEntity users, String content){
        return BoardReplyEntity.builder()
                .boardEntity(board)
                .usersEntity(users)
                .content(content)
                .build();
    }

    default BlameEntity dtoToEntityReport(BoardReplyEntity reply, UsersEntity users, String contents){
        return BlameEntity.builder()
                .usersEntity(users)
                .boardReplyEntity(reply)
                .category((byte)1)
                .content(contents)
                .build();
    }
}
