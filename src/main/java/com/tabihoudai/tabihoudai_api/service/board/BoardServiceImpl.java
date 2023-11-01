package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardListDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardRegisterDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardViewDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    private final BoardReplyRepository boardReplyRepository;
    @Override
    public List<BoardViewDTO> get(long boardIdx) {
        List<Object[]> result = boardRepository.getByBoardIdx(boardIdx);
        List<BoardViewDTO> collect =
                result.stream()
                        .map(obj -> entityToViewDTO((BoardEntity) obj[0], (UsersEntity) obj[1], (Long) obj[2], (Long) obj[3]))
                        .collect(Collectors.toList());
        return collect;
    }


    @Transactional
    @Override
    public List<BoardListDTO> getList(int categoryNo) {
        List<Object[]> result = boardRepository.getBoardByCategory(categoryNo);
        List<BoardListDTO> collect = result.stream().map(objects -> entityToListDTO((BoardEntity) objects[0], (UsersEntity) objects[1]))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long registerBoard(BoardRegisterDTO dto) {
        log.info("dto : {}", dto);
        BoardEntity boardEntity = dtoToEntityRegister(dto);
        boardRepository.save(boardEntity);
        return boardEntity.getBoardIdx();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeBoard(Long boardIdx) {
        boardReplyRepository.deleteById(boardIdx);
        boardRepository.deleteById(boardIdx);
    }

}
