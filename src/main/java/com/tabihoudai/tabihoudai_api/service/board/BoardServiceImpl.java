package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.board.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.board.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    private final BoardReplyRepository boardReplyRepository;

    @Transactional
    @Override
    public List<BoardDTO.BoardViewDTO> get(long boardIdx) {
        List<Object[]> result = boardRepository.getByBoardIdx(boardIdx);

        List<BoardDTO.BoardViewDTO> collect = result.stream()
                .map(obj -> entityToViewDTO((BoardEntity) obj[0], (UsersEntity) obj[1], (Long) obj[2], (Long) obj[3], (Long) obj[4]))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long registerBoard(BoardDTO.BoardRegisterDTO dto) {
        log.info("dto : {}", dto);
        BoardEntity boardEntity = dtoToEntityRegister(dto);
        boardRepository.save(boardEntity);
        return boardEntity.getBoardIdx();
    }

    @Override
    public PageResultDTO getList(PageRequestDTO pageRequestDTO, Integer category) {
        Page<Object[]> boardList = boardRepository.getBoardList(pageRequestDTO.getPageable(), category);
        Function<Object[], BoardDTO.BoardListDTO> fn = objects -> boardListEntityToDTO((BoardEntity) objects[0], (UsersEntity) objects[1]);
        return new PageResultDTO(boardList, fn);
    }

    @Override
    public PageResultDTO getListNullCategory(PageRequestDTO pageRequestDTO) {
        Page<Object[]> boardList = boardRepository.getListNullCategory(pageRequestDTO.getPageable());
        Function<Object[], BoardDTO.BoardListDTO> fn = objects -> boardListEntityToDTO((BoardEntity) objects[0], (UsersEntity) objects[1]);
        return new PageResultDTO(boardList, fn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeBoard(Long boardIdx) {
        boardReplyRepository.deleteById(boardIdx);
        boardRepository.deleteById(boardIdx);
    }

    @Override
    public void modifyBoard(BoardDTO.BoardRegisterDTO dto) {
        Optional<BoardEntity> result = boardRepository.findById(dto.getBoardIdx());
        if (result.isPresent()){
            BoardEntity board = result.get();
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());
            board.changeCategory(dto.getCategory());
            boardRepository.save(board);
        }
    }
}
