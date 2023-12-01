package com.tabihoudai.tabihoudai_api.service.board;

import com.tabihoudai.tabihoudai_api.dto.board.BoardLikeDTO;
import com.tabihoudai.tabihoudai_api.entity.board.BoardLikeEntity;
import com.tabihoudai.tabihoudai_api.repository.board.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardLikeServiceImpl implements BoardLikeService{

    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    @Override
    public void insertLike(BoardLikeDTO.LikeInsertDTO dto) throws Exception {
        if (boardLikeRepository.findByBoardAndUsers(dto.getBoardIdx(), dto.getUserIdx()).isPresent()){
            throw new Exception();
        }

        BoardLikeEntity result = dtoToEntityLike(dto);

        boardLikeRepository.save(result);
    }
}
