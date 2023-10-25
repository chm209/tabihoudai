package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    private final BoardReplyRepository boardReplyRepository;
    @Override
    public List<BoardDTO> get(long boardIdx) {
        List<Object[]> result = boardRepository.getByBoardIdx(boardIdx);
        List<BoardDTO> collect =
                result.stream()
                        .map(obj -> entityToDTO((BoardEntity) obj[0], (UsersEntity) obj[1], (Long) obj[2], (Long) obj[3]))
                        .collect(Collectors.toList());
        return collect;
    }
}
