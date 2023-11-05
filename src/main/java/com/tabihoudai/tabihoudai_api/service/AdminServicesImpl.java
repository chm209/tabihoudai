package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttrMngRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlockEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.BlockRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.CsRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionImageRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardLikeRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.board.BoardRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanLikeRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServicesImpl implements AdminServices {

    private final BannerRepository bannerRepository;
    private final BoardReplyRepository boardReplyRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final PlanRepository planRepository;
    private final PlanLikeRepository planLikeRepository;
    private final PlanReplyRepository planReplyRepository;
    private final BlameRepository blameRepository;
    private final BlockRepository blockRepository;
    private final CsRepository csRepository;
    private final AttractionRepository attractionRepository;
    private final AttractionImageRepository attractionImageRepository;
    private final AttractionReplyRepository attractionReplyRepository;
    private final RegionRepository regionRepository;
    private final UsersRepository usersRepository;

    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;

    @Override
    public String deleteAdminItem(int item, long idx) {
        if (item == 1) { // 배너 이미지
            BannerEntity banner = bannerRepository.findByBannerIdx(idx);
            // 로컬에서 이미지 삭제
            File file = new File(banner.getPath());
            if (file.exists()) {
                if (file.delete()) System.out.println("이미지 삭제 성공");
                else System.out.println("이미지 삭제 실패");
            } else System.out.println("파일이 존재하지 않습니다.");
            bannerRepository.deleteByBannerIdx(idx);
        } else if (item == 2) { // 관광 명소 관리
            List<AttractionImageEntity> attrImage = attractionImageRepository.findByAttrEntity_AttrIdx(idx);
            String[] path = attrImage.get(0).getPath().split("/");
            String forderPath = "";
            for (String str : path) {
                if (str.equals(path[path.length - 1])) break;
                forderPath = forderPath.concat(str + File.separator);
            }
            File folder = new File(forderPath);

            try {
                while (folder.exists()) {
                    File[] folderList = folder.listFiles();

                    for (int offset = 0; offset < folderList.length; offset++) {
                        folderList[offset].delete();
                        System.out.println("파일 삭제");
                    }
                    if (folderList.length == 0 && folder.isDirectory()) {
                        folder.delete();
                        System.out.println("폴더 삭제");
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
            }

            attractionImageRepository.deleteByAttrEntity_AttrIdx(idx);
            attractionReplyRepository.deleteByAttrEntity_AttrIdx(idx);
            attractionRepository.deleteByAttrIdx(idx);
        } else if (item == 3) { // 신고 관리
            blameRepository.deleteByBlameIdx(idx);
        } else if (item == 4) { // 문의 관리
            csRepository.deleteByCsIdx(idx);
        }
        return "";
    }

    @Override
    public String craeteAttraction(AttrMngRequestDTO requestDTO) {
        // requestDTO로 RegionEntity를 가져온다
        RegionEntity regionEntity = regionRepository.findRegionEntityByAreaAndCity(requestDTO.getArea(), requestDTO.getCity());
        // idx 생성을 위해 기존 정보를 가져온다.
        List<AttractionEntity> attrList = attractionRepository.findAllByRegionEntity_RegionIdx(regionEntity.getRegionIdx());
        log.info("{}", regionEntity.getRegionIdx());
        log.info("{}", attrList.size());
        log.info("{}", attrList.get(attrList.size() - 1).getAttrIdx());
        long attractionOffset = Long.parseLong(String.valueOf(attrList.get(attrList.size() - 1).getAttrIdx()).substring(2)) + 1;
        // Entity 생성
        log.info("{}", attractionOffset);
        AttractionEntity attrEntity = AttractionEntity.builder()
                .attrIdx(Long.parseLong(String.valueOf(regionEntity.getRegionIdx()).concat(String.valueOf(attractionOffset))))
                .address(requestDTO.getAddress())
                .description(requestDTO.getDescription())
                .latitude(requestDTO.getLatitude())
                .longitude(requestDTO.getLongitude())
                .attraction(requestDTO.getAttraction())
                .tag(requestDTO.getTag())
                .regionEntity(regionEntity)
                .build();
        attractionRepository.save(attrEntity);

        // 이미지 추가
        if (requestDTO.getImages().get(0) != null) {
            int imageIdx = 1;
            long attrIdx = attractionOffset;

            for (MultipartFile createImage : requestDTO.getImages()) {
                if (createImage.getContentType().startsWith("image") == false) break;
                // 경로 지정
                String fileName = createImage.getOriginalFilename();
                String folderPath = makeForder("attraction/" + attrEntity.getAttrIdx());
                String save = uploadPath + File.separator + folderPath + File.separator + fileName;
                Path imageSavePath = Paths.get(save);

                try {
                    createImage.transferTo(imageSavePath);
                } catch (IOException e) {
                }
                // DB에 추가
                AttractionImageEntity attractionImageEntity = attrImgDtoToEntity(
                        imageSavePath,
                        requestDTO.getThumbnail(),
                        attrEntity,
                        createImage,
                        Long.valueOf(String.valueOf(regionEntity.getRegionIdx()).concat(String.valueOf(attrIdx).concat(String.valueOf(imageIdx++))))
                );
                attractionImageRepository.save(attractionImageEntity);
            }
        }
        return "success";
    }

    @Override
    public String patchAttraction(AttrMngRequestDTO requestDTO) {
        // requestDTO로 RegionEntity를 가져온다
        RegionEntity regionEntity = regionRepository.findRegionEntityByAreaAndCity(requestDTO.getArea(), requestDTO.getCity());
        // 기존 데이터와 비교를 위해 기존 정보를 가져온다.
        AttractionEntity originalAttrEntity = attractionRepository.findByAttrIdx(requestDTO.getAttrIdx());
        // RegionEntity로 Attraction Entity를 생성한다.
        // 기존 데이터와 비교후 바뀐 데이터를 반영해서 엔티티를 만든다.
        AttractionEntity attrEntity = attrDtoToEntity(originalAttrEntity, requestDTO, regionEntity);
        // Attraction 정보부터 patch 한다.
        attractionRepository.patchAttraction(attrEntity);

        // 기존 이미지를 List로 받아온다.
        List<AttractionImageEntity> attrImage = attractionImageRepository.findAllByAttrEntityAttrIdx(attrEntity.getAttrIdx());
        int size = attrImage.size();
        // 삭제할 이미지가 있는지 없는지 검사해서 삭제할 이미지가 있다면
        if (!requestDTO.getRmImg().isEmpty()) {
            // 삭제할 이미지의 리스트를 split으로 나눠 배열로 지정해준다.
            String[] removeImages = requestDTO.getRmImg().split(",");
            // DB에서 이미지를 삭제한다.
            for (String rmImage : removeImages) {
                AttractionImageEntity findImageData = attractionImageRepository.findByAttrImgIdx(Long.parseLong(rmImage.replaceAll("[^0-9]", "")));
                attractionImageRepository.deleteByAttrImgIdx(Long.parseLong(rmImage.replaceAll("[^0-9]", "")));
                // 저장소에서 이미지 삭제
                File file = new File(findImageData.getPath());
                if (file.exists()) {
                    if (file.delete()) System.out.println("이미지 삭제 성공");
                    else System.out.println("이미지 삭제 실패");
                } else System.out.println("파일이 존재하지 않습니다.");
            }
            // attrImgIdx를 새로 설정해주기 위한 준비
            String attrIdxBase = String.valueOf(requestDTO.getAttrIdx());
            // List<AttractionImageEntity> attractionImageEntities = attractionImageRepository.findAllByAttrEntityAttrIdx(requestDTO.getAttrIdx());
            // 새로 할당
            for (int offset = 0; offset < size; offset++) {
                attractionRepository.patchAttrImgIdx(attrImage.get(offset), requestDTO.getAttrIdx(), Long.parseLong(attrIdxBase.concat(String.valueOf(offset + 1))));
            }
        }
        // 추가한 이미지가 있으면 추가
        if (requestDTO.getImages().get(0) != null) {
            // 새로 받은 이미지 추가
            for (MultipartFile uploadFile : requestDTO.getImages()) {
                if (uploadFile.getContentType().startsWith("image") == false) break;
                // 경로 지정
                String fileName = uploadFile.getOriginalFilename();
                String folderPath = makeForder("attraction/" + attrEntity.getAttrIdx());
                String save = uploadPath + File.separator + folderPath + File.separator + fileName;
                Path imageSavePath = Paths.get(save);

                // 중복 검사
                AttractionImageEntity checkFile = attractionImageRepository.findByPath(imageSavePath.toString());
                if (checkFile == null) {
                    try {
                        uploadFile.transferTo(imageSavePath);
                    } catch (IOException e) {
                    }
                    // DB에 추가
                    AttractionImageEntity attractionImageEntity = attrImgDtoToEntity(
                            imageSavePath,
                            requestDTO.getThumbnail(),
                            attrEntity,
                            uploadFile,
                            Long.valueOf(String.valueOf(requestDTO.getAttrIdx()).concat(String.valueOf(++size)))
                    );
                    attractionImageRepository.save(attractionImageEntity);
                } else System.out.println("중복");
            }
        }
        // 메인 이미지 변경
        attrImage = attractionImageRepository.findAllByAttrEntityAttrIdx(attrEntity.getAttrIdx());
        for (AttractionImageEntity offset : attrImage) {
            String folderPath = makeForder("attraction/" + attrEntity.getAttrIdx());
            String save = uploadPath + File.separator + folderPath + File.separator + requestDTO.getThumbnail();
            attractionRepository.patchThumnails(save, offset.getAttrImgIdx());
        }
        return "성공";
    }

    @Override
    public PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO) {
        if (item == 1) { // 배너 이미지
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("bannerIdx").descending());
            Page<Object[]> list = bannerRepository.getBannerPage(pageRequest);
            Page<AdminDTO.bannerInfo> result = list.map(objects -> bannerEntityToDto(objects));
            return new PageResultDTO<>(result);
        } else if (item == 2) { // 관광 명소 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("attrIdx").ascending());
            Long regionIdx = Long.parseLong(pageRequestDTO.getSearchArea()) + Long.parseLong(pageRequestDTO.getSearchCity());
            String[] region = (regionRepository.getAreaCity(regionIdx)).split(",");
            Page<Object[]> list = attractionRepository.getAttractionPage(region[0], region[1], pageRequest);
            Page<AdminDTO.attrInfo> result = list.map(objects -> attrEntityToDto(objects));
            return new PageResultDTO<>(result);
        } else if (item == 3) { // 신고 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("blameIdx").descending());
            Page<Object[]> list = blameRepository.getBlamePage(pageRequest);
            Page<AdminDTO.blameInfo> result = list.map(objects -> blameEntityToDto(objects));
            return new PageResultDTO<>(result);
        } else { // 문의 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("csIdx").descending());
            Page<Object[]> list = csRepository.getCsPage(pageRequest);
            Page<AdminDTO.csInfo> result = list.map(objects -> csEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
    }

    @Override
    public AdminDTO.attrDetailData getAttrDetailData(long attrIdx) {
        List<AttractionImageEntity> attrImageResult = attractionImageRepository.findAllByAttrEntityAttrIdx(attrIdx);
        return attrDetailEntityToDto(attrImageResult);
    }

    @Override
    public AdminDTO.blameDetailInfo getBlameDetailViewer(long blameIdx) {
        BlameEntity blameEntity = blameRepository.findByBlameIdx(blameIdx);
        return blameEntityToDto(blameEntity);
    }

    @Override
    public String userBlockManager(long blameIdx, AdminDTO.userBlockRequestDto userBlockRequestDto) {
        BlockEntity originalBlock = blockRepository.findByUsersEntity_UserIdx(userBlockRequestDto.getUserIdx());
        Calendar calendar = Calendar.getInstance();

        // 차단인지 검사
        if (userBlockRequestDto.getFlag() == 0) { // 차단
            // 기존 차단 내역
            BlameEntity blame = blameRepository.findByBlameIdx(blameIdx);
            // 유저Idx랑 비교해서 동일한지 검사
            if (blame.getUsersEntity().getUserIdx().equals(userBlockRequestDto.getUserIdx())) {
                // + 차단 해제
                // 0 == 차단, 1 == 해제
                usersRepository.patchUsersBlockCondition(blame.getUsersEntity(), 0); // 유저 차단
                if (originalBlock == null) { // 첫 차단
                    calendar.add(Calendar.DATE, 3);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = BlockEntity.builder()
                            .count((byte) 1)
                            .endDate(endDate)
                            .usersEntity(blame.getUsersEntity())
                            .build();
                    blockRepository.save(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequestDto); // 컨텐츠 삭제
                } else if (originalBlock.getCount() == 1) { // 두 번째 차단
                    calendar.add(Calendar.DATE, 7);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = BlockEntity.builder()
                            .count((byte) 2)
                            .endDate(endDate)
                            .usersEntity(blame.getUsersEntity())
                            .build();
                    blockRepository.patchBlockManager(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequestDto); // 컨텐츠 삭제
                } else if (originalBlock.getCount() == 2) { // 세 번째 차단 (영구 차단)
                    calendar.add(Calendar.YEAR, 100);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = BlockEntity.builder()
                            .count((byte) 3)
                            .endDate(endDate)
                            .usersEntity(blame.getUsersEntity())
                            .build();
                    blockRepository.patchBlockManager(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequestDto); // 컨텐츠 삭제
                }
            } else return "유저 정보가 올바르지 않습니다.";
            return "차단 완료";
        } else {
            UsersEntity user = usersRepository.findByUserIdx(userBlockRequestDto.getUserIdx());
            // 유저 테이블에서 블락 0 으로 변경
            usersRepository.patchUsersBlockCondition(user, 1); // 유저 차단
            // 블락 테이블에서 1회면 삭제
            // 2회 이상은 1 차감
            if (originalBlock.getCount() > 1) { // 차감
                java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis()); // 시간 리셋
                BlockEntity newBlock = BlockEntity.builder()
                        .count((byte) (originalBlock.getCount() - 1))
                        .endDate(endDate)
                        .usersEntity(user)
                        .build();
                blockRepository.patchBlockManager(newBlock); // 차단
            } else { // 삭제
                blockRepository.deleteByBlockIdx(originalBlock.getBlockIdx());
            }
            return "차단 해제";
        }
    }

    @Override
    public String uploadBannerImage(MultipartFile uploadFile) {
        if (uploadFile.getContentType().startsWith("image") == false) {
            return "fail";
        }
        String fileName = uploadFile.getOriginalFilename();
        String folderPath = makeForder("banner");
        String saveName = uploadPath + File.separator + folderPath + File.separator + fileName;
        Path path = Paths.get(saveName);
        try {
            uploadFile.transferTo(path);
            bannerRepository.save(BannerEntity.builder().path(saveName).build());
        } catch (IOException e) {
        }
        return "success";
    }

    private void blockAndDelete(BlameEntity blame, AdminDTO.userBlockRequestDto userBlockRequestDto) {
        // CASCADE.ALL은 자제하는게 좋다고 해서 직접 지워줌
        if (blame.getBoardReplyEntity() != null && (blame.getBoardReplyEntity().getBoardReplyIdx() == userBlockRequestDto.getContentIdx())) {
            blameRepository.deleteByBlameIdx(blame.getBlameIdx());
            boardReplyRepository.deleteByBoardReplyIdx(userBlockRequestDto.getContentIdx());
        } else if (blame.getAttrReplyEntity() != null && (blame.getAttrReplyEntity().getAttrReplyIdx() == userBlockRequestDto.getContentIdx())) {
            blameRepository.deleteByBlameIdx(blame.getBlameIdx());
            attractionReplyRepository.deleteByAttrReplyIdx(userBlockRequestDto.getContentIdx());
        } else if (blame.getBoardEntity() != null && (blame.getBoardEntity().getBoardIdx() == userBlockRequestDto.getContentIdx())) {
            blameRepository.deleteByBlameIdx(blame.getBlameIdx());
            boardReplyRepository.deleteByBoardEntity_BoardIdx(userBlockRequestDto.getContentIdx());
            boardLikeRepository.deleteByBoardEntity_BoardIdx(userBlockRequestDto.getContentIdx());
            boardRepository.deleteByBoardIdx(userBlockRequestDto.getContentIdx());
        } else if (blame.getPlanEntity() != null && (blame.getPlanEntity().getPlanIdx() == userBlockRequestDto.getContentIdx())) {
            blameRepository.deleteByBlameIdx(blame.getBlameIdx());
            planLikeRepository.deleteByPlanEntity_PlanIdx(userBlockRequestDto.getContentIdx());
            planReplyRepository.deleteByPlanEntity_PlanIdx(userBlockRequestDto.getContentIdx());
            planRepository.deleteByPlanIdx(userBlockRequestDto.getContentIdx());
        } else if (blame.getPlanReplyEntity() != null && (blame.getPlanReplyEntity().getPlanReplyIdx() == userBlockRequestDto.getContentIdx())) {
            blameRepository.deleteByBlameIdx(blame.getBlameIdx());
            planReplyRepository.deleteByPlanReplyIdx(userBlockRequestDto.getContentIdx());
        }
    }

    private String makeForder(String path) {
        String[] loactions = path.split("/");
        String fullLoaction = loactions[0];

        switch (loactions[0]) {
            case "banner":
                break;
            case "attraction":
                for (String location : loactions) {
                    if (fullLoaction.equals(location)) continue;
                    fullLoaction = fullLoaction.concat(File.separator + location);
                }
                break;
        }
        File uploadPathFolder = new File(uploadPath, fullLoaction);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return fullLoaction;
    }
}
