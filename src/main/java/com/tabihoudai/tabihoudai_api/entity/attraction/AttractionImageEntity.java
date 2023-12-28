package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "attr_img")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class AttractionImageEntity {
    @Id
    @Column(name = "attr_img_idx")
    private long attrImgIdx;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private char type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attrIdx", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AttractionEntity attrEntity;
}
