package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface AdminServices {

    PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO);
    String uploadBannerImage(MultipartFile uploadFile);
    AdminDTO.attrDetailData getAttrDetailData(long attrIdx);
    String patchAttraction(AttrMngRequestDTO attrMngRequestDTO);
    String craeteAttraction(AttrMngRequestDTO requestDTO);
    String deleteAdminItem(int item, long idx);

    default AttractionEntity attrDtoToEntity(AttractionEntity originalAttrEntity, AttrMngRequestDTO request, RegionEntity regionEntity) {
        return AttractionEntity.builder()
                .attrIdx(request.getAttrIdx() == originalAttrEntity.getAttrIdx() ? originalAttrEntity.getAttrIdx() : request.getAttrIdx())
                .address(request.getAddress().equals(originalAttrEntity.getAddress()) ? originalAttrEntity.getAddress() : request.getAddress())
                .description(request.getDescription().equals(originalAttrEntity.getDescription()) ? originalAttrEntity.getDescription() : request.getDescription())
                .latitude(request.getLatitude() == originalAttrEntity.getLatitude() ? originalAttrEntity.getLatitude() : request.getLatitude())
                .longitude(request.getLongitude() == originalAttrEntity.getLongitude() ? originalAttrEntity.getLongitude() : request.getLongitude())
                .attraction(request.getAttraction().equals(originalAttrEntity.getAttraction()) ? originalAttrEntity.getAttraction() : request.getAttraction())
                .tag(request.getTag().equals(originalAttrEntity.getTag()) ? originalAttrEntity.getTag() : request.getTag())
                .regionEntity(regionEntity.equals(originalAttrEntity.getRegionEntity()) ? originalAttrEntity.getRegionEntity() : regionEntity)
                .build();
    }

    default AttractionImageEntity attrImgDtoToEntity(Path imageSavePath, String thumbnails, AttractionEntity attrEntity, MultipartFile image, Long attrImgIdx) {
        return AttractionImageEntity.builder()
                .attrImgIdx(attrImgIdx)
                .path(imageSavePath.toString())
                .type(image.getOriginalFilename().equals(thumbnails) ? '1' : '0')
                .attrEntity(attrEntity)
                .build();
    }

    default AdminDTO.attrDetailData attrDetailEntityToDto(List<AttractionImageEntity> attrImageResult) {
        return AdminDTO.attrDetailData.builder()
                .attrIdx(attrImageResult.get(0).getAttrEntity().getAttrIdx())
                .name(attrImageResult.get(0).getAttrEntity().getAttraction())
                .area(attrImageResult.get(0).getAttrEntity().getRegionEntity().getArea())
                .city(attrImageResult.get(0).getAttrEntity().getRegionEntity().getCity())
                .address(attrImageResult.get(0).getAttrEntity().getAddress())
                .latitude(attrImageResult.get(0).getAttrEntity().getLatitude())
                .longitude(attrImageResult.get(0).getAttrEntity().getLongitude())
                .description(attrImageResult.get(0).getAttrEntity().getDescription())
                .tag(attrImageResult.get(0).getAttrEntity().getTag())
                .attrDetailImageData(attrImageResult.stream().map(attractionImageEntity -> attrImageDetailEntityToDto(attractionImageEntity)).collect(Collectors.toList()))
                .build();
    }

    default AdminDTO.attrDetailImageData attrImageDetailEntityToDto(AttractionImageEntity attrImageResult) {
        return AdminDTO.attrDetailImageData.builder()
                .path(attrImageResult.getPath())
                .type(attrImageResult.getType())
                .attrImgIdx(attrImageResult.getAttrImgIdx())
                .build();
    }

    // 배너 이미지 관리 리스트
    default AdminDTO.bannerInfo bannerEntityToDto(Object[] bannerEntity) {
        return AdminDTO.bannerInfo.builder()
                        .bannerIdx((Long) bannerEntity[0])
                        .path((String) bannerEntity[1])
                        .regDate((LocalDateTime) bannerEntity[2])
                        .build();
    }

    default AdminDTO.blameInfo blameEntityToDto(Object[] blameEntity) {
        return AdminDTO.blameInfo.builder()
                        .blameIdx((Long) blameEntity[0])
                        .category((byte) blameEntity[1])
                        .content((String) blameEntity[2])
                        .build();
    }

    default AdminDTO.csInfo csEntityToDto(Object[] csEntity) {
        return AdminDTO.csInfo.builder()
                        .csIdx((Long) csEntity[0])
                        .nickname((String) csEntity[1])
                        .type((byte) csEntity[2])
                        .content((String) csEntity[3])
                        .build();
    }

    default AdminDTO.attrInfo attrEntityToDto(Object[] attrEntity) {
        return AdminDTO.attrInfo.builder()
                        .attrIdx((Long) attrEntity[0])
                        .area((String) attrEntity[1])
                        .city((String) attrEntity[2])
                        .address((String) attrEntity[3])
                        .name((String) attrEntity[4])
                        .build();
    }
}