package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardReplyServiceImpl implements BoardReplyService{

    private final BoardReplyRepository boardReplyRepository;

    @Override
    public List<BoardReplyDTO.getReplyDTO> getReply(Long boardIdx){
        List<Object[]> result = boardReplyRepository.getReplyByBoardIdx(boardIdx);
        List<BoardReplyDTO.getReplyDTO> collect = result.stream().map(objects -> entityToGetDTO((BoardReplyEntity) objects[0], (UsersEntity) objects[1]))
                .collect(Collectors.toList());
        return collect;
    }
}