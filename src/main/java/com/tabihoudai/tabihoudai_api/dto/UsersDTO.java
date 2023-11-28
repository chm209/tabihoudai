package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.service.UsersService;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class UsersDTO {

    private final UsersService usersService;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    private String pw;

    private String email;

    private String nickname;

    private java.sql.Date birthday;

    private MultipartFile image;

    private String profile;

    public UsersEntity usersDtoToEntity() {

        return UsersEntity.builder()
                .userIdx(userIdx)
                .pw(pw)
                .birthday(birthday)
                .nickname(nickname)
                .email(email)
                .build();
    }

}
