package com.tabihoudai.tabihoudai_api.repository.users;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersEntity, UUID> {
    UsersEntity findByUserIdx(UUID userIdx);

    UsersEntity findByEmail(String email);

    UsersEntity findByNickname(String nickname);

    @Modifying
    @Query("UPDATE UsersEntity u " +
            "SET u.email = :#{#usersEntity.email}, u.birthday = :#{#usersEntity.birthday}, u.pw = :#{#usersEntity.pw} u.nickname = :#{#usersEntity.nickname}, u.profile = :#{#usersEntity.profile}")
    void editUsersInfo(@Param("usersEntity") UsersEntity usersEntity);
}
