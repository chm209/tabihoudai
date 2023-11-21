package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_user")
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SEQ_USER_IDX")
    @SequenceGenerator(name = "SEQ_USER_IDX",
            sequenceName = "SEQUENCE_USER_IDX", allocationSize = 1)
    @Column(name = "user_idx")
    private byte[] userIdx;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String block;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    private String nickname;


}
