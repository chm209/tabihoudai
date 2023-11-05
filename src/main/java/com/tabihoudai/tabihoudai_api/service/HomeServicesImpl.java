package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionImageRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServicesImpl implements HomeServices{

    private final BannerRepository bannerRepository;
    private final AttractionRepository attractionRepository;
    private final AttractionImageRepository attractionImageRepository;
    private final BoardRepository boardRepository;
    private final PlanRepository planRepository;
    private final RegionRepository regionRepository;

    @Override
    public AdminDTO.bannerInfoResponse getBanner() {
        List<BannerEntity> bannerList = bannerRepository.findAll();
        List<AdminDTO.mainBannerData> bannerListDto = bannerList.stream().map(this::entityToDto).collect(Collectors.toList());
        return new AdminDTO.bannerInfoResponse<>(bannerListDto);
    }

    @Override
    public AttractionDTO.attractionInfoResponse getAttraction(int area, Integer city) {
        List<Object[]> result;

        if(city == null) { // main page
            result = attractionRepository.findAllByRegionEntity_RegionIdx_area((long) area / 10);
            Collections.shuffle(result);
            for (int offset = result.size()-1; offset >= 3; offset--) {
                result.remove(offset);
            }
        }
        else { // region selector page
            result = attractionRepository.findAllByRegionEntity_RegionIdx_city((long) city);
            Collections.shuffle(result);
            for (int offset = result.size()-1; offset >= 6; offset--) {
                result.remove(offset);
            }
        }
        List<AttractionDTO.mainAttractionData> collect = result.stream().map(this::entityToDto).collect(Collectors.toList());
        return new AttractionDTO.attractionInfoResponse<>(collect);
    }




    @Override
    public List<BoardDTO.recentBoard> getBoard() {
        List<Object[]> boardList = boardRepository.getRecentBoard();
        return boardList.stream().map(this::recentBoardEntityToDto).collect(Collectors.toList());
    }

    @Override
    public AttractionDTO.resultCity<AttractionDTO.cityList> getCityList(Integer area) {
        List<RegionEntity> regionEntities = regionRepository.findCity((long) (area / 10));
        List<AttractionDTO.cityList> result = regionEntities.stream().map(regionEntity -> regionEntityToDto(regionEntity)).collect(Collectors.toList());
        return new AttractionDTO.resultCity(result);
    }

    @Override
    public List<PlanDTO.bestPlan> getBestPlan(Integer area) {
        List<Object[]> planList = (area == null ? planRepository.getBestPlan() : planRepository.getAreaBestPlan(areaFactory(area)));
        List<String> planAttr = planList.stream().map(this::planImage).toList();
        String[] str = planAttr.get(0).split(",");
        String thumbnails = str[0];
        AttractionImageEntity bestAttrImage = attractionImageRepository.getInfo(Long.parseLong(thumbnails));
        return planList.stream().map(objects -> bestPlanEntityToDto(objects, bestAttrImage)).collect(Collectors.toList());
    }
}