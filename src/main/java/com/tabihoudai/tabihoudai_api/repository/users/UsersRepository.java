package com.tabihoudai.tabihoudai_api.repository.users;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersEntity, UUID>, UsersRepositoryCustom {

    UsersEntity findByEmail(@Param("email") String email);

    UsersEntity findByUserIdx(@Param("userIdx") UUID userIdx);

    @Query("SELECT u FROM UsersEntity u WHERE u.nickname = :nickname")
    UsersEntity findByName(@Param("nickname")String nickname);
}
