package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardLikeDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardLikeEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

public interface BoardLikeService {

    void insertLike(BoardLikeDTO.LikeInsertDTO dto) throws Exception;

    default BoardLikeEntity dtoToEntityLike(BoardLikeDTO.LikeInsertDTO dto){
        BoardEntity board = BoardEntity.builder().boardIdx(dto.getBoardIdx()).build();
        UsersEntity users = UsersEntity.builder().userIdx(dto.getUserIdx()).build();
        return BoardLikeEntity.builder()
                .flag(dto.getFlag())
                .boardEntity(board)
                .usersEntity(users)
                .build();
    }
}
