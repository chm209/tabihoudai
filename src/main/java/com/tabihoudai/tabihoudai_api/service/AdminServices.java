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

    String deleteAdminItem(int item, long idx);

    String craeteAttraction(AdminDTO.attrCreateModifyRequestList requestDTO);

    String uploadBannerImage(MultipartFile uploadFile);

    AdminDTO.attrModifyDataResponse getModifyAttractionData(long attrIdx);

    String modifyAttractionData(AdminDTO.attrCreateModifyRequestList attrCreateModifyRequestList);

    AdminDTO.csViewerResponse getCSViewer(long csIdx);

    String insertCsReply(long csIdx, AdminDTO.CsReplyRequest csReplyRequest);

    void blockCron();

    AdminDTO.blameViewerResponse getBlameViewerData(long blameIdx);
    String userBlockManager(long id, AdminDTO.userBlockRequest userBlockRequest);

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

    default AdminDTO.attrModifyDataResponse attrModiftDataEntityToDto(List<AttractionImageEntity> attraction) {
        return AdminDTO.attrModifyDataResponse.builder()
                .attrIdx(attraction.get(0).getAttrEntity().getAttrIdx())
                .name(attraction.get(0).getAttrEntity().getAttraction())
                .area(attraction.get(0).getAttrEntity().getRegionEntity().getArea())
                .city(attraction.get(0).getAttrEntity().getRegionEntity().getCity())
                .address(attraction.get(0).getAttrEntity().getAddress())
                .latitude(attraction.get(0).getAttrEntity().getLatitude())
                .longitude(attraction.get(0).getAttrEntity().getLongitude())
                .description(attraction.get(0).getAttrEntity().getDescription())
                .tag(attraction.get(0).getAttrEntity().getTag())
                .attrImgList(attraction.stream().map(this::attrModifyImgDataEntityToDto).collect(Collectors.toList()))
                .build();
    }

    default AdminDTO.attrModifyImgDataResponse attrModifyImgDataEntityToDto(AttractionImageEntity attractionImage) {
        return AdminDTO.attrModifyImgDataResponse.builder()
                .path(attractionImage.getPath())
                .attrImgIdx(attractionImage.getAttrImgIdx())
                .build();
    }

    default AttractionEntity dtoToEntity(AttractionEntity originalAttrEntity, AdminDTO.attrCreateModifyRequestList request, RegionEntity regionEntity) {
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

    default AttractionImageEntity dtoToEntity(Path imageSavePath, String thumbnails, AttractionEntity attrEntity, MultipartFile image, Long attrImgIdx) {
        return AttractionImageEntity.builder()
                .attrImgIdx(attrImgIdx)
                .path(imageSavePath.toString())
                .type(image.getOriginalFilename().equals(thumbnails) ? '1' : '0')
                .attrEntity(attrEntity)
                .build();
    }

    default AdminDTO.csViewerResponse entityToDto(CsEntity csEntity) {
        return AdminDTO.csViewerResponse.builder()
                .userIdx(csEntity.getUsersEntity().getUserIdx())
                .askDate(csEntity.getAskDate())
                .title(csEntity.getTitle())
                .content(csEntity.getContent())
                .reply(csEntity.getReply())
                .replyDate(csEntity.getReplyDate())
                .build();
    }

    default AdminDTO.blameViewerResponse entityToDto(BlameEntity blame) {
        return AdminDTO.blameViewerResponse.builder()
                .userIdx(blame.getUsersEntity().getUserIdx())
                .blameIdx(blame.getBlameIdx())
                .blameContentIdx(!(blame.getAttrReplyEntity() == null) ? blame.getAttrReplyEntity().getAttrReplyIdx()
                        : !(blame.getBoardEntity() == null) ? blame.getBoardEntity().getBoardIdx()
                        : !(blame.getBoardReplyEntity() == null) ? blame.getBoardReplyEntity().getBoardReplyIdx()
                        : !(blame.getPlanEntity() == null) ? blame.getPlanEntity().getPlanIdx()
                        : blame.getPlanReplyEntity().getPlanReplyIdx()
                )
                .blameReason(blame.getCategory())
                .blameContent(blame.getContent())
                .date(blame.getBlameDate())
                .build();
    }
}