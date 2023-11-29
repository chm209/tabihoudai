package com.tabihoudai.tabihoudai_api.controller;


import com.tabihoudai.tabihoudai_api.dto.board.BoardReplyDTO;
import com.tabihoudai.tabihoudai_api.service.board.BoardReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/write")
    public String registReply(
            @RequestBody BoardReplyDTO.replyRegisterDTO dto
    ){
        boardReplyService.registerReply(dto);
        return "댓글이 작성되었습니다.";
    }

    @DeleteMapping("/delete/{replyIdx}")
    public String removeReply(
            @PathVariable("replyIdx") Long replyIdx
    ){
        boardReplyService.removeReply(replyIdx);
        return "댓글이 삭제되었습니다.";
    }

    @PostMapping("/report")
    public String reportBoard(
            @RequestBody BoardReplyDTO.reportReplyDTO dto
            ){
        boardReplyService.reportReply(dto);
        return "신고가 완료되었습니다.";
    }
}
