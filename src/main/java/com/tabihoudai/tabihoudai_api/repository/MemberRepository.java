package com.tabihoudai.tabihoudai_api.repository;

import com.tabihoudai.tabihoudai_api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT MAX(userIdx) FROM Member")
    Long getMaxUserIdx();
}
