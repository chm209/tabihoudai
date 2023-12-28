package com.tabihoudai.tabihoudai_api.entity.admin;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "cs")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CsEntity {
    @Id
    @Column(name = "cs_idx")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CS")
    @SequenceGenerator(name = "SEQ_CS", sequenceName = "SEQUENCE_CS", allocationSize = 1)
    private long csIdx;

    @Column(nullable = false)
    private byte type;

    @Column(nullable = false)
    private String title;

    @Column(name = "ask_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate askDate;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String reply;

    // updatable은 컬럼을 수정한 이후 들어오는 데이터를 막는 것이고
    // updatable은 컬럼을 수정한 이후 기존에 저장되어 있던 데이터를 수정할 수 없게끔 막는다.
    // 참고: https://www.inflearn.com/questions/122517/column-%EC%86%8D%EC%84%B1-insertable-updatable%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C
    @Column(nullable = true, updatable = true, name = "reply_date")
    private LocalDate replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UsersEntity usersEntity;
}
