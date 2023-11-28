package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.entity.Member;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void resetPassword(String email, String newPassword) throws EmailNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 새로운 비밀번호를 암호화하여 저장
            String encodedPassword = passwordEncoder.encode(newPassword);
            member.setPassword(encodedPassword);

            memberRepository.save(member);
        } else {
            throw new EmailNotFoundException("해당 이메일 주소로 등록된 사용자가 없습니다.");
        }
    }
}