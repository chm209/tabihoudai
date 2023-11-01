package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.service.EmailNotFoundException;
import com.tabihoudai.tabihoudai_api.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // 이메일 주소를 받아옵니다.
        String newPassword = request.get("newPassword"); // 새로운 비밀번호를 받아옵니다.

        try {
            passwordService.resetPassword(email, newPassword); // 비밀번호 초기화 및 변경 서비스 호출
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }
}
