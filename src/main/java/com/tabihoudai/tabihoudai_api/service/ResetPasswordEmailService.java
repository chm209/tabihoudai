package com.tabihoudai.tabihoudai_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordEmailService {

    private final JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("비밀번호 초기화");
        message.setText("비밀번호를 초기화하려면 다음 링크를 클릭하세요: " + resetLink);

        javaMailSender.send(message);
    }
}
