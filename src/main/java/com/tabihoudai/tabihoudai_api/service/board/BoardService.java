package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

import java.util.List;

public interface BoardService {

    List<BoardDTO.BoardViewDTO> get(long boardIdx);

    Long registerBoard(BoardDTO.BoardRegisterDTO dto);

    BoardDTO.PageResultDTO<BoardDTO.BoardListDTO, Object[]> getList(BoardDTO.PageRequestDTO pageRequestDTO, Integer category);

    BoardDTO.PageResultDTO<BoardDTO.BoardListDTO, Object[]> getSearchList(BoardDTO.PageRequestDTO pageRequestDTO, String keyword, String type);

    void removeBoard(Long boardIdx);

    void modifyBoard(BoardDTO.BoardRegisterDTO dto);

    void reportBoard(BoardDTO.reportBoardDTO dto);

    default BoardDTO.BoardViewDTO entityToViewDTO(BoardEntity board, UsersEntity users, Long replyCount, Long likeCount, Long unLikeCount){
        return BoardDTO.BoardViewDTO.builder()
                .boardIdx(board.getBoardIdx())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .visitCount(board.getVisitCount())
                .nickname(users.getNickname())
                .replyCount(replyCount.intValue())
                .likeCount(likeCount.intValue())
                .unLikeCount(unLikeCount.intValue())
                .build();
    }

    default BoardEntity dtoToEntityRegister(BoardDTO.BoardRegisterDTO dto){
        UsersEntity users = UsersEntity.builder().userIdx(dto.getUsersIdx()).build();
        return BoardEntity.builder()
                .usersEntity(users)
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    default BoardDTO.BoardListDTO boardListEntityToDTO(BoardEntity board, UsersEntity users){
        return BoardDTO.BoardListDTO.builder()
                .boardIdx(board.getBoardIdx())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .visitCount(board.getVisitCount())
                .nickname(users.getNickname())
                .build();
    }

    default BlameEntity dtoToEntityReport(BoardEntity board, UsersEntity users, String contents){
        return BlameEntity.builder()
                .usersEntity(users)
                .boardEntity(board)
                .category((byte)0)
                .content(contents)
                .build();
    }
}
