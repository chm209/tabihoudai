package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.entity.Member;
import com.tabihoudai.tabihoudai_api.entity.Profile;
import com.tabihoudai.tabihoudai_api.entity.RefreshToken;
import com.tabihoudai.tabihoudai_api.entity.Role;
import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.repository.MemberRepository;
import com.tabihoudai.tabihoudai_api.security.jwt.util.IfLogin;
import com.tabihoudai.tabihoudai_api.security.jwt.util.JwtTokenizer;
import com.tabihoudai.tabihoudai_api.security.jwt.util.LoginUserDto;
import com.tabihoudai.tabihoudai_api.service.MemberService;
import com.tabihoudai.tabihoudai_api.service.ProfileService;
import com.tabihoudai.tabihoudai_api.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ProfileService profileService;

    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;

    @GetMapping("/check-email/{email}")
    public ResponseEntity<String> checkEmailExistence(@PathVariable String email) {
        // DB에서 중복 체크
        boolean emailExists = memberRepository.existsByEmail(email);

        if (emailExists) {
            return ResponseEntity.ok("이미 사용 중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }

    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<String> checkNicknameExistence(@PathVariable String nickname) {
        // DB에서 중복 체크
        boolean existsByNickname = memberRepository.existsByNickname(nickname);

        if (existsByNickname) {
            return ResponseEntity.ok("이미 사용 중인 닉네임입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = memberSignupDto.getEmail();
        String nickname = memberSignupDto.getNickname();

        // DB에서 중복 체크
        boolean emailExists = memberRepository.existsByEmail(email);

        boolean existsByNickname = memberRepository.existsByNickname(nickname);

        if (emailExists) {
            return new ResponseEntity("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        if (existsByNickname) {
            return new ResponseEntity("이미 존재하는 닉네임 입니다.", HttpStatus.BAD_REQUEST);
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

            Profile profile = createProfile(saveMember);
            profileService.saveProfile(profile);

            // 회원가입
            return new ResponseEntity(memberSignupResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(
            MultipartFile[] file
    ) {

         List<UploadResultDTO> resultDTOList
                = new ArrayList<>();

        for (MultipartFile uploadFile :
                file) {

            if (uploadFile.getContentType().startsWith("image") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String originalFilename = uploadFile.getOriginalFilename();
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            log.info("filename : {}", fileName);

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName =
                    uploadPath + File.separator +
                            folderPath + File.separator +
                            uuid + "_" + fileName;

            Path path = Paths.get(saveName);
            try {

                uploadFile.transferTo(path);

                String thumbnailSaveName =
                        uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(path.toFile(), thumbnailFile, 250, 210);

                resultDTOList.add(
                        new UploadResultDTO(fileName, uuid, folderPath)
                );
            } catch (IOException e) {
            }
        }
        return ResponseEntity.ok(resultDTOList);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        );
        String folderPath = str.replace("//", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findByEmail(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getUserIdx(), member.getEmail(), member.getNickname(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getUserIdx(), member.getEmail(), member.getNickname(), roles);

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

    private Profile createProfile(Member member) {
        // 여기에서 프로필 이미지 정보를 생성하고 반환
        String uuid = UUID.randomUUID().toString();
        String imgName = "default_profile_image.jpg";
        String path = "/images/default/";

        Profile profile = Profile.builder()
                .uuid(uuid)
                .imgName(imgName)
                .path(path)
                .member(member)
                .build();

        return profile;
    }

}