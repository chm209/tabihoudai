package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.entity.Profile;
import com.tabihoudai.tabihoudai_api.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }

    // 기타 필요한 메서드 추가 가능
}

