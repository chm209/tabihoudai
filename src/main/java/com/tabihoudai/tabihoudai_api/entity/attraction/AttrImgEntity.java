package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_attr_img")
@Getter
public class AttrImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ATTR_IMG_IDX")
    @SequenceGenerator(name = "SEQ_ATTR_IMG_IDX",
            sequenceName = "SEQUENCE_ATTR_IMG_IDX", allocationSize = 1)
    @Column(name = "attr_img_idx")
    private Long attrImgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attr_idx")
    private AttractionEntity attrIdx;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private char type;

}
