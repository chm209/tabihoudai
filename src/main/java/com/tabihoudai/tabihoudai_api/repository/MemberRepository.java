package com.tabihoudai.tabihoudai_api.repository;

import com.tabihoudai.tabihoudai_api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
