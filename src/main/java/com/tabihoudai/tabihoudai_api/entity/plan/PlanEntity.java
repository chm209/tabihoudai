package com.tabihoudai.tabihoudai_api.entity.plan;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan")
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAN")
    @SequenceGenerator(name = "SEQ_PLAN", sequenceName = "SEQUENCE_PLAN", allocationSize = 1)
    private long planIdx;

    @Column(nullable = false)
    private int adult;

    @Column(nullable = false)
    private int child;

    @Column(nullable = true)
    private int budget;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private java.sql.Date dateFrom;

    @Column(nullable = false)
    private java.sql.Date dateTo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String content;

    @ColumnDefault("0")
    private int visitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;

    @Column(nullable = false)
    private String attrList;


}
