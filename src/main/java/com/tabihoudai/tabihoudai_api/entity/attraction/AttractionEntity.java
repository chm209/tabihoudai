package com.tabihoudai.tabihoudai_api.entity.attraction;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attr")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class AttractionEntity {
    @Id
    private long attrIdx;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = RegionEntity.class)
    @JoinColumn(name = "regionIdx", nullable = false)
    private RegionEntity regionEntity;
}
