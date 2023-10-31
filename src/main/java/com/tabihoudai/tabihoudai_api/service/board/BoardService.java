package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardListDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardRegisterDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardViewDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardService {

    default BoardViewDTO entityToViewDTO(BoardEntity board, UsersEntity users, Long replyCount, Long likeCount){
        return BoardViewDTO.builder()
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

    default BoardListDTO entityToListDTO(BoardEntity board, UsersEntity users){
        return BoardListDTO.builder().
                boardIdx(board.getBoardIdx())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .visitCount(board.getVisitCount())
                .nickname(users.getNickname())
                .build();
    }

    default BoardEntity dtoToEntityRegister(BoardRegisterDTO dto){
        UsersEntity users = UsersEntity.builder().userIdx(dto.getUsersIdx()).build();
        return BoardEntity.builder()
                .usersEntity(users)
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    List<BoardViewDTO> get(long boardIdx);

    List<BoardListDTO> getList(int category);

    Long registerBoard(BoardRegisterDTO dto);

}
