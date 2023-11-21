package com.tabihoudai.tabihoudai_api.entity.admin;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "blame")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class BlameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BLAME")
    @SequenceGenerator(name = "SEQ_BLAME", sequenceName = "SEQUENCE_BLAME", allocationSize = 1)
    private long blameIdx;

    @Column(nullable = false)
    private LocalDate blameDate;

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
}
