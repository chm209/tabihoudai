package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServicesImpl implements HomeServices{

    private final BannerRepository bannerRepository;

    @Override
    public List<AdminDTO.bannerInfo> getBanner() {
        List<BannerEntity> entityList = bannerRepository.findAll();
        return entityList.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
