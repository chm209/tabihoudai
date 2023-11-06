package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.domain.Member;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import com.tabihoudai.tabihoudai_api.service.ResetPasswordEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password-reset")
public class ResetPasswordController {

    private final ResetPasswordEmailService resetPasswordEmailService;
    private final MemberRepository memberRepository;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendResetEmail(@RequestParam String email) {
        // 여기서는 DB에서 이메일이 있는지 검사합니다.
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (!memberOptional.isPresent()) {
            return ResponseEntity.badRequest().body("등록되지 않은 이메일입니다.");
        }

        Member member = memberOptional.get();

        // 이메일이 존재하면 임의로 랜덤한 초기화 링크를 생성합니다.
        String resetLink = "http://localhost:5173/resetpw/reset-password?token=randomtoken";

        resetPasswordEmailService.sendResetPasswordEmail(email, resetLink);

        return ResponseEntity.ok("이메일이 성공적으로 전송되었습니다.");
    }
}
