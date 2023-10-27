package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServicesImpl implements AdminServices {

    @Autowired
    private final BannerRepository bannerRepository;

    @Autowired
    private final BlameRepository blameRepository;

    @Autowired
    private final CsRepository csRepository;

    @Override
    public PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO) {
        if(item == 1) { // 배너 이미지
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("bannerIdx").descending());
            Page<Object[]> list = bannerRepository.getBannerPage(pageRequest);
            Page<AdminDTO.bannerInfo> result = list.map(objects -> bannerEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
        else if(item == 2) { // 관광 명소 관리
        
        }
        else if(item == 3) { // 신고 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("blameIdx").descending());
            Page<Object[]> list = blameRepository.getBlamePage(pageRequest);
            Page<AdminDTO.blameInfo> result = list.map(objects -> blameEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
        else { // 문의 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("csIdx").descending());
            Page<Object[]> list = csRepository.getCsPage(pageRequest);
            Page<AdminDTO.csInfo> result = list.map(objects -> csEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
    }
}
