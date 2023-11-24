package com.tabihoudai.tabihoudai_api.entity.board;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_reply")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class BoardReplyEntity {
    @Id
    @Column(name = "board_reply_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_REPLY")
    @SequenceGenerator(name = "SEQ_BOARD_REPLY", sequenceName = "SEQUENCE_BOARD_REPLY", allocationSize = 1)
    private long boardReplyIdx;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false, name = "reg_date")
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx", nullable = false)
    private BoardEntity boardEntity;
}
