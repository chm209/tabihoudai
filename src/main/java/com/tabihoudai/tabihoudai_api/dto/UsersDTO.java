package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Builder
@ToString
public class UsersDTO {

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    private String pw;

    private String email;

    private String nickname;

    private java.sql.Date birthday;

    private String profile;

    public UsersEntity usersDtoToEntity() {

        return UsersEntity.builder()
                .userIdx(userIdx)
                .pw(pw)
                .birthday(birthday)
                .nickname(nickname)
                .email(email)
                .profile(profile)
                .build();
    }

}
