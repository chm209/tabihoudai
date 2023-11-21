package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.board.BoardDTO;
import com.tabihoudai.tabihoudai_api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    

    @GetMapping("/{boardIdx}")
    public ResponseEntity viewBoard(@PathVariable long boardIdx){
        log.info("boardIdx = " + boardIdx);
        return ResponseEntity.status(HttpStatus.OK).body(boardService.get(boardIdx).get(0));
    }

    @PostMapping("/write")
    public ResponseEntity register(@RequestBody BoardDTO.BoardRegisterDTO dto){
        log.info(dto.toString());
        Long boardIdx = boardService.registerBoard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardIdx.toString());
    }

    @DeleteMapping("/{boardIdx}")
    public String remove(@PathVariable("boardIdx")Long boardIdx){
        log.info("boardIdx : {}",boardIdx);
        return "success";
    }
}
