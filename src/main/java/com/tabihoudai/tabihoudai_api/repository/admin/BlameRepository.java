package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.querydsl.QueryBlameRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlameRepository extends JpaRepository<BlameEntity,Long>, QueryBlameRepository {
}
