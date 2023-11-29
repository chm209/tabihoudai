package com.tabihoudai.tabihoudai_api.entity.attraction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="tbl_attraction")
@Getter
public class AttractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ATTR_IDX")
    @SequenceGenerator(name = "SEQ_ATTR_IDX",
            sequenceName = "SEQUENCE_ATTR_IDX", allocationSize = 1)
    @Column(name = "attr_idx")
    private Long attrIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "region_idx")
    private RegionEntity regionIdx;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private String attraction;

    @Column(nullable = false)
    private String tag;

}
