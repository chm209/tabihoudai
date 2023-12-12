package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,byte[]> {
    UserEntity findAllByUserIdx(byte[] userIdx);

    Optional<UserEntity> findByEmail(String email);
}
