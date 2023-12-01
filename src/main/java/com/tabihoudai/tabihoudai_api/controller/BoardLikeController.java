package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.board.BoardLikeDTO;
import com.tabihoudai.tabihoudai_api.service.board.BoardLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
@Slf4j
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("")
    public String postLike(
            @RequestBody BoardLikeDTO.LikeInsertDTO dto
            ) throws Exception {
        boardLikeService.insertLike(dto);
        return "좋아요 하셨습니다.";
    }

}
