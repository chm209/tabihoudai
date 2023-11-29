package com.tabihoudai.tabihoudai_api.entity.attraction;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_attr_reply")
@Getter
public class AttrReplyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ATTR_REPLY_IDX")
    @SequenceGenerator(name = "SEQ_ATTR_REPLY_IDX",
            sequenceName = "SEQUENCE_ATTR_REPLY_IDX", allocationSize = 1)
    @Column(name = "attr_reply_idx")
    private Long attrReplyIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "attr_idx")
    private AttractionEntity attrIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="user_idx")
    private UserEntity userIdx;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double score;

    private String path;

}
