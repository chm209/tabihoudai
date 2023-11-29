package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardReplyServiceImpl implements BoardReplyService{

    private final BoardReplyRepository boardReplyRepository;

    private final BlameRepository blameRepository;
    @Override
    public List<BoardReplyDTO.getReplyDTO> getReply(Long boardIdx){
        List<Object[]> result = boardReplyRepository.getReplyByBoardIdx(boardIdx);
        List<BoardReplyDTO.getReplyDTO> collect = result.stream().map(objects -> entityToGetDTO((BoardReplyEntity) objects[0], (UsersEntity) objects[1]))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void registerReply(BoardReplyDTO.replyRegisterDTO dto) {
        BoardEntity board = BoardEntity.builder().boardIdx(dto.getBoardIdx()).build();
        UsersEntity users = UsersEntity.builder().userIdx(dto.getUsersIdx()).build();
        BoardReplyEntity reply = dtoToEntityReply(board, users, dto.getContent());
        boardReplyRepository.save(reply);
    }

    @Override
    public void reportReply(BoardReplyDTO.reportReplyDTO dto) {
        BoardReplyEntity reply = boardReplyRepository.findByBoardReplyIdx(dto.getReplyIdx());
        UsersEntity users = reply.getUsersEntity();
        BlameEntity blameEntity = dtoToEntityReport(reply, users, dto.getContent());
        blameRepository.save(blameEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeReply(Long replyIdx) {
        blameRepository.deleteReplyBlame(replyIdx);
        boardReplyRepository.deleteById(replyIdx);
    }
}
