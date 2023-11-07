package com.tabihoudai.tabihoudai_api.api.users;

import com.tabihoudai.tabihoudai_api.dao.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.CsRepository;

import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/profileview")
@RequiredArgsConstructor
public class ProfileViewController {

    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final BoardReplyRepository boardReplyRepository;
    @Autowired
    private final PlanReplyRepository planReplyRepository;
    @Autowired
    private final CsRepository csRepository;
    @Autowired
    private final PlanRepository planRepository;

    @GetMapping("/{userIdx}")
    public ResponseEntity<Map<String, Object>> getUserData(@PathVariable UUID userIdx) {
        Map<String, Object> userData = new HashMap<>();

        List<Map<String, Object>> cs = csRepository.findByUserIdx(userIdx);
        userData.put("cs", cs);

        List<Map<String, Object>> plan = planRepository.findByUserIdx(userIdx);
        userData.put("plan", plan);

        List<Map<String, Object>> board = boardRepository.findByUserIdx(userIdx);
        userData.put("board", board);

        List<Map<String, Object>> boardReply = boardReplyRepository.findByUserIdx(userIdx);
        userData.put("boardReply", boardReply);

        List<Map<String, Object>> planReply = planReplyRepository.findByUserIdx(userIdx);
        userData.put("planReply", planReply);

        return ResponseEntity.ok(userData);
    }




}
