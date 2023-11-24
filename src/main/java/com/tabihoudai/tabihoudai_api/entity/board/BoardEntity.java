package com.tabihoudai.tabihoudai_api.entity.board;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class BoardEntity {
    @Id
    @Column(name = "board_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD")
    @SequenceGenerator(name = "SEQ_BOARD", sequenceName = "SEQUENCE_BOARD", allocationSize = 1)
    private long boardIdx;

    @Column(nullable = false)
    private int category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "reg_date")
    private LocalDateTime regDate;

    @Column(nullable = false, name = "visit_count")
    @ColumnDefault("0")
    private int visitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;
}
