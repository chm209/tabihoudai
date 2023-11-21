package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "region")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class RegionEntity {
    @Id
    private long regionIdx;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String city;
}
