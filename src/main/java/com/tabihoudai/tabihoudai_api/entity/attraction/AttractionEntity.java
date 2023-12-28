package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "attr")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class AttractionEntity {
    @Id
    @Column(name = "attr_idx")
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "regionIdx", nullable = false)
    private RegionEntity regionEntity;
}
