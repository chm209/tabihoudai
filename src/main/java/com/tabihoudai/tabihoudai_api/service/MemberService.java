package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.entity.Member;
import com.tabihoudai.tabihoudai_api.entity.Role;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import com.tabihoudai.tabihoudai_api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }

    @Transactional
    public Member addMember(Member member) {
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        member.addRole(userRole.get());
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    private String saveProfileImage(MultipartFile profileImage) {
        try {
            String uploadDir = "C:\\Users\\cksdu\\Desktop\\폴더\\프로젝트\\tabi_back\\profile";
            Files.createDirectories(Path.of(uploadDir));

            String fileName = System.currentTimeMillis() + "-" + profileImage.getOriginalFilename();
            Path filePath = Path.of(uploadDir, fileName);

            Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 저장 실패", e);
        }
    }


    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long userIdx){
        return memberRepository.findById(userIdx);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMember(String email){
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkEmailExistence(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameExistence(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
