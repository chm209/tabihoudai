package com.tabihoudai.tabihoudai_api.repository;

import com.tabihoudai.tabihoudai_api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
