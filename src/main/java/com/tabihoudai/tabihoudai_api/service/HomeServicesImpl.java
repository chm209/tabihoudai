package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServicesImpl implements HomeServices{

    private final BannerRepository bannerRepository;
    private final AttractionRepository attractionRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<AdminDTO.bannerInfo> getBanner() {
        List<BannerEntity> entityList = bannerRepository.findAll();
        return entityList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<AttractionDTO.attrPreview> getAttraction(int area, Integer city) {
        List<Object[]> resultEntity;
        if(city == null) {
            resultEntity = attractionRepository.findAllByRegionEntity_RegionIdx_area((long) area / 10);
            Collections.shuffle(resultEntity);
            for (int k = resultEntity.size()-1; k >= 3; k--) {
                resultEntity.remove(k);
            }
        }
        else {
            resultEntity = attractionRepository.findAllByRegionEntity_RegionIdx_city((long) city);
            Collections.shuffle(resultEntity);
            for (int k = resultEntity.size()-1; k >= 6; k--) {
                resultEntity.remove(k);
            }
        }
        return resultEntity.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardDTO.recentBoard> getBoard() {
        List<Object[]> boardList = boardRepository.getRecentBoard();
        return boardList.stream().map(this::recentBoardEntityToDto).collect(Collectors.toList());
    }
}





















