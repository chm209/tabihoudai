package com.tabihoudai.tabihoudai_api.entity.admin;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "blame")
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BlameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BLAME")
    @SequenceGenerator(name = "SEQ_BLAME", sequenceName = "SEQUENCE_BLAME", allocationSize = 1)
    private long blameIdx;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime blameDate;

    @Column(nullable = false)
    private byte category;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attrIdx", nullable = true)
    private AttractionEntity attrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attrReplyIdx", nullable = true)
    private AttractionReplyEntity attrReplyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx", nullable = true)
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardReplyIdx", nullable = true)
    private BoardReplyEntity boardReplyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planIdx", nullable = true)
    private PlanEntity planEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planReplyIdx", nullable = true)
    private PlanReplyEntity planReplyEntity;
}
