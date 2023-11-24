package com.tabihoudai.tabihoudai_api.entity.admin;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "banner")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class BannerEntity {

    @Id
    @Column(name = "banner_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BANNER")
    @SequenceGenerator(name = "SEQ_BANNER", sequenceName = "SEQUENCE_BANNER", allocationSize = 1)
    private long bannerIdx;

    @Column(nullable = false)
    private String path;

    @Column(name = "reg_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime regDate;
}