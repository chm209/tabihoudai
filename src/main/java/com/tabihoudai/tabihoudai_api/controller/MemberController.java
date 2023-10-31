package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.domain.Member;
import com.tabihoudai.tabihoudai_api.domain.RefreshToken;
import com.tabihoudai.tabihoudai_api.domain.Role;
import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import com.tabihoudai.tabihoudai_api.security.jwt.util.IfLogin;
import com.tabihoudai.tabihoudai_api.security.jwt.util.JwtTokenizer;
import com.tabihoudai.tabihoudai_api.security.jwt.util.LoginUserDto;
import com.tabihoudai.tabihoudai_api.service.MemberService;
import com.tabihoudai.tabihoudai_api.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @PostMapping("/check-email")
    public ResponseEntity checkEmailExistence(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity("올바르지 않은 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        String email = memberSignupDto.getEmail();

        // DB에서 중복 체크
        boolean emailExists = memberRepository.existsByEmail(email);

        if (emailExists) {
            return new ResponseEntity("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("사용 가능한 이메일입니다.", HttpStatus.OK);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = memberSignupDto.getEmail();

        // DB에서 중복 체크
        boolean emailExists = memberRepository.existsByEmail(email);

        if (emailExists) {
            return new ResponseEntity("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = new Member();
        member.setNickname(memberSignupDto.getNickname());
        member.setEmail(memberSignupDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));

        String birthdayString = memberSignupDto.getBirthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthday = LocalDate.parse(birthdayString, formatter);
        member.setBirthday(birthday);

        try {
            Member saveMember = memberService.addMember(member);

            MemberSignupResponseDto memberSignupResponse = new MemberSignupResponseDto();
            memberSignupResponse.setUserIdx(saveMember.getUserIdx());
            memberSignupResponse.setNickname(saveMember.getNickname());
            memberSignupResponse.setRegdate(saveMember.getRegdate());
            memberSignupResponse.setEmail(saveMember.getEmail());
            memberSignupResponse.setBirthday(saveMember.getBirthday());

            // 회원가입
            return new ResponseEntity(memberSignupResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
        Member member = memberService.findByEmail(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // List<Role> ===> List<String>
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getUserIdx(), member.getEmail(), member.getNickname(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getUserIdx(), member.getEmail(), member.getNickname(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는 것이 좋다.
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserIdx(member.getUserIdx());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userIdx(member.getUserIdx())
                .nickname(member.getNickname())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
    2. RefreshToken이 유효한지 체크한다.
    3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
     */
    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long userIdx = Long.valueOf((Integer) claims.get("userIdx"));

        Member member = memberService.getMember(userIdx).orElseThrow(() -> new IllegalArgumentException("Member not found"));


        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(userIdx, email, member.getNickname(), roles);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .userIdx(member.getUserIdx())
                .nickname(member.getNickname())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity userinfo(@IfLogin LoginUserDto loginUserDto) {
        Member member = memberService.findByEmail(loginUserDto.getEmail());
        return new ResponseEntity(member, HttpStatus.OK);
    }

}
