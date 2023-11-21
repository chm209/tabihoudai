package com.tabihoudai.tabihoudai_api.entity.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class UsersEntity {

    @Id
    @Column(columnDefinition = "RAW(16)")
    private UUID userIdx;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = true)
    private java.sql.Date birthday;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private byte block;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    private String nickname;
}
