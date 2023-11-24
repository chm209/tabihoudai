package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardReplyServiceImpl implements BoardReplyService{

    private final BoardReplyRepository boardReplyRepository;
}
