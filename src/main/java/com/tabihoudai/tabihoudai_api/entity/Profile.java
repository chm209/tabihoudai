package com.tabihoudai.tabihoudai_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    @SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQUENCE_PROFILE_IMAGE", allocationSize = 1)
    private Long inum;

    // UUID
    private String uuid;

    private String imgName;
    private String path;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
}
