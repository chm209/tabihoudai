package com.tabihoudai.tabihoudai_api.entity.attraction;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_region")
@Getter
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SEQ_REGION_IDX")
    @SequenceGenerator(name = "SEQ_REGION_IDX",
            sequenceName = "SEQUENCE_REGION_IDX", allocationSize = 1)
    @Column(name = "region_idx")
    private Long regionIdx;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String city;

}
