package com.tabihoudai.tabihoudai_api.entity.admin;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "banner")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class BannerEntity {

    @Id
    private long bannerIdx;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false, updatable = false)
    private LocalDateTime regDate;
}