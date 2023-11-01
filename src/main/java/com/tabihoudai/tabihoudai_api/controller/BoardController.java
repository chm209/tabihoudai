package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.board.BoardListDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardRegisterDTO;
import com.tabihoudai.tabihoudai_api.dto.board.BoardViewDTO;
import com.tabihoudai.tabihoudai_api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{boardIdx}")
    public List<BoardViewDTO> viewBoard(@PathVariable long boardIdx){
        log.info("boardIdx = " + boardIdx);
        List<BoardViewDTO> dto = boardService.get(boardIdx);
        return dto;
    }


    @GetMapping("")
    public List<BoardListDTO> listBoard(@RequestParam(value = "category") int category){
        List<BoardListDTO> list = boardService.getList(category);
        return list;
    }

    @PostMapping("")
    public ResponseEntity register(@RequestBody BoardRegisterDTO dto){
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
