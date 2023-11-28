package com.tabihoudai.tabihoudai_api.controller;


import com.tabihoudai.tabihoudai_api.service.board.BoardReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
@Slf4j
public class BoardReplyController {

    private final BoardReplyService boardReplyService;

    @GetMapping("/{boardIdx}")
    public ResponseEntity getReplyList(@PathVariable long boardIdx){
        return ResponseEntity.status(HttpStatus.OK).body(boardReplyService.getReply(boardIdx));
    }

}
