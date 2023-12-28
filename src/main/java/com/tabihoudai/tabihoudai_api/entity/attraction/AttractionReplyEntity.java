package com.tabihoudai.tabihoudai_api.entity.attraction;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "attr_reply")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class AttractionReplyEntity {
    @Id
    @Column(name = "attr_reply_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ATTR_REPLY")
    @SequenceGenerator(name = "SEQ_ATTR_REPLY", sequenceName = "SEQUENCE_ATTR_REPLY", allocationSize = 1)
    private long attrReplyIdx;

    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int score;

    @Column(nullable = true)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attrIdx", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AttractionEntity attrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;
}
