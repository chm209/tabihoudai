package com.tabihoudai.tabihoudai_api.entity.plan;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class PlanReplyEntity {
    @Id
    @Column(name = "plan_reply_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAN_REPLY")
    @SequenceGenerator(name = "SEQ_PLAN_REPLY", sequenceName = "SEQUENCE_PLAN_REPLY", allocationSize = 1)
    private long planReplyIdx;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "reg_date")
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "planIdx", nullable = false)
    private PlanEntity planEntity;
}
