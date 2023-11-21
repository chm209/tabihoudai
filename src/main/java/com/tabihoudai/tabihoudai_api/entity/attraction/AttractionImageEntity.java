package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attr_img")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class AttractionImageEntity {
    @Id
    private long attrImgIdx;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private char type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attrIdx", nullable = false)
    private AttractionEntity attrEntity;
}
