package com.tabihoudai.tabihoudai_api.entity.plan;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class PlanEntity {

    @Id
    @Column(name = "plan_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAN")
    @SequenceGenerator(name = "SEQ_PLAN", sequenceName = "SEQUENCE_PLAN", allocationSize = 1)
    private long planIdx;

    @Column(nullable = false)
    private int adult;
    @Column(nullable = false)
    private int child;

    @Column(nullable = true)
    private int budget;

    @Column(nullable = false, updatable = false, name = "reg_date")
    private LocalDateTime regDate;

    @Column(nullable = false, name = "date_from")
    private java.sql.Date dateFrom;

    @Column(nullable = false, name = "date_to")
    private java.sql.Date dateTo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = false, name = "attr_list")
    private String attrList;

    @Column(nullable = false, name = "visit_count")
    @ColumnDefault("0")
    private int visitCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;
}
