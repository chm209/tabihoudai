package com.tabihoudai.tabihoudai_api.repository.users;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersEntity, UUID> {
    UsersEntity findByUserIdx(UUID userIdx);

    UsersEntity findByEmail(String email);

    UsersEntity findByNickname(String nickname);
}
