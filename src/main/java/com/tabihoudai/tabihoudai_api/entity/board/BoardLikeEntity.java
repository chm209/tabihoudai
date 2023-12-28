package com.tabihoudai.tabihoudai_api.entity.board;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "board_like")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class BoardLikeEntity {
    @Id
    @Column(name = "board_like_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_LIKE")
    @SequenceGenerator(name = "SEQ_BOARD_LIKE", sequenceName = "SEQUENCE_BOARD_LIKE", allocationSize = 1)
    private long boardLikeIdx;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private byte flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx", nullable = false)
    private BoardEntity boardEntity;
}
