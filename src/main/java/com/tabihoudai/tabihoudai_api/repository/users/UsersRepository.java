package com.tabihoudai.tabihoudai_api.repository.users;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersEntity, UUID> {

    @Query("SELECT u FROM UsersEntity u WHERE u.nickname = :nickname")
    UsersEntity findByName(@Param("nickname")String nickname);

    UsersEntity findByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)//fetch 조인을 어노테이션으로 사용, load : 자기가 선언한 속성으로 가져온다.
    @Query("select u " +
            "from UsersEntity u " +
            "where u.email = :email")
    Optional<UsersEntity> findByEmailLogIn(
            @Param("email") String email);
}
