package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.Role;
import com.tabihoudai.tabihoudai_api.entity.attraction.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,byte[]> {

    @Transactional
    @Modifying
    @Query(value = "INSERT into tbl_user VALUES(utl_raw.cast_to_raw(SEQUENCE_USER_IDX.NEXTVAL),:block,:email,:nickname,:profile,:pw,:#{#role.name()})",nativeQuery = true)
    void saveUser(@Param("block") String block, @Param("email")String email, @Param("nickname") String nickname, @Param("profile") String profile,@Param("pw")String pw, @Param("role")Role role);

    UserEntity findAllByUserIdx(byte[] userIdx);

    Optional<UserEntity> findByEmail(String email);



}
