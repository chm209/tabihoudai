package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.entity.Member;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import com.tabihoudai.tabihoudai_api.service.ResetPasswordEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password-reset")
public class ResetPasswordController {

    private final ResetPasswordEmailService resetPasswordEmailService;
    private final MemberRepository memberRepository;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendResetEmail(@RequestBody Member member) {
        // 이메일 검사를 위해 Member 엔티티로부터 이메일을 가져옴.
        String email = member.getEmail();

        // 여기서는 DB에서 이메일이 있는지 검사.
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (!memberOptional.isPresent()) {
            return ResponseEntity.badRequest().body("등록되지 않은 이메일입니다.");
        }

        // 이메일이 존재하면 임의로 랜덤한 초기화 링크를 생성.
        String resetLink = "http://localhost:5173/resetpw";

        resetPasswordEmailService.sendResetPasswordEmail(email, resetLink);

        return ResponseEntity.ok("이메일이 성공적으로 전송되었습니다.");
    }
}
