package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
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
    AdminDTO.adminPageResponse getAdminManagementList(int item, AdminDTO.adminPageRequestList pageRequestDTO);
    String craeteAttraction(AdminDTO.attrCreateModifyRequestList requestDTO);

    default AdminDTO.adminBannerList bannerPageEntityToDto(Object[] bannerPage) {
        return AdminDTO.adminBannerList.builder()
                        .bannerIdx((Long) bannerPage[0])
                        .path((String) bannerPage[1])
                        .regDate((LocalDateTime) bannerPage[2])
                        .build();
    }

    default AdminDTO.adminAttrList attrPageEntityToDto(Object[] attrPage) {
        return AdminDTO.adminAttrList.builder()
                        .attrIdx((Long) attrPage[0])
                        .area((String) attrPage[1])
                        .city((String) attrPage[2])
                        .address((String) attrPage[3])
                        .name((String) attrPage[4])
                        .build();
    }

    default AdminDTO.adminBlameList blamePageEntityToDto(Object[] blamePage) {
        return AdminDTO.adminBlameList.builder()
                        .blameIdx((Long) blamePage[0])
                        .category((byte) blamePage[1])
                        .content((String) blamePage[2])
                        .build();
    }

       default AdminDTO.adminCsList csPageEntityToDto(Object[] csPage) {
        return AdminDTO.adminCsList.builder()
                        .csIdx((Long) csPage[0])
                        .nickname((String) csPage[1])
                        .type((byte) csPage[2])
                        .content((String) csPage[3])
                        .build();
    }












    String uploadBannerImage(MultipartFile uploadFile);
    AdminDTO.attrDetailData getAttrDetailData(long attrIdx);
    String patchAttraction(AdminDTO.attrCreateModifyRequestList attrMngRequestDTO);

    String deleteAdminItem(int item, long idx);
    AdminDTO.blameDetailInfo getBlameDetailViewer(long blameIdx);
    String userBlockManager(long id, AdminDTO.userBlockRequestDto userBlockRequestDto);
    AdminDTO.csDetailInfo getCsDetailViewer(long csIdx);
    String postCsReply(long csIdx, AdminDTO.CsReplyRequestDto csReplyRequestDto);
    void blockCron();

    default AttractionEntity attrDtoToEntity(AttractionEntity originalAttrEntity, AdminDTO.attrCreateModifyRequestList request, RegionEntity regionEntity) {
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
    default AdminDTO.csDetailInfo csEntityToDto(CsEntity csEntity) {
        return AdminDTO.csDetailInfo.builder()
                .userIdx(csEntity.getUsersEntity().getUserIdx())
                .askDate(csEntity.getAskDate())
                .title(csEntity.getTitle())
                .content(csEntity.getContent())
                .reply(csEntity.getReply())
                .replyDate(csEntity.getReplyDate())
                .build();
    }



    default AdminDTO.blameDetailInfo blameEntityToDto(BlameEntity blameEntity) {
        return AdminDTO.blameDetailInfo.builder()
                .userIdx(blameEntity.getUsersEntity().getUserIdx())
                .blameIdx(blameEntity.getBlameIdx())
                .contentIdx(!(blameEntity.getAttrReplyEntity() == null) ? blameEntity.getAttrReplyEntity().getAttrReplyIdx()
                                : !(blameEntity.getBoardEntity() == null) ? blameEntity.getBoardEntity().getBoardIdx()
                                : !(blameEntity.getBoardReplyEntity() == null) ? blameEntity.getBoardReplyEntity().getBoardReplyIdx()
                                : !(blameEntity.getPlanEntity() == null) ? blameEntity.getPlanEntity().getPlanIdx()
                                : blameEntity.getPlanReplyEntity().getPlanReplyIdx()
                )
                .blameContent(blameEntity.getCategory())
                .content(blameEntity.getContent())
                .date(blameEntity.getBlameDate())
                .build();
    }
}