package com.tabihoudai.tabihoudai_api.entity.attraction;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value= {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @Column(name = "regdate",updatable = false)
    @CreatedDate
    private LocalDateTime regDate;


}
