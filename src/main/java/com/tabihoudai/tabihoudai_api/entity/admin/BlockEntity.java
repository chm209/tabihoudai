package com.tabihoudai.tabihoudai_api.entity.admin;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "block")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BlockEntity {
    @Id
    @Column(name = "block_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BLOCK")
    @SequenceGenerator(name = "SEQ_BLOCK", sequenceName = "SEQUENCE_BLOCK", allocationSize = 1)
    private long blockIdx;

    @Column(nullable = false)
    private byte count;

    @CreatedDate
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private java.util.Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersEntity usersEntity;
}
