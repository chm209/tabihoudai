package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlockEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServicesImpl implements AdminServices {
    // user
    private final UsersRepository usersRepository;
    // admin
    private final BannerRepository bannerRepository;
    private final BlameRepository blameRepository;
    private final BlockRepository blockRepository;
    private final CsRepository csRepository;
    // board
    private final BoardReplyRepository boardReplyRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    // plan
    private final PlanRepository planRepository;
    private final PlanLikeRepository planLikeRepository;
    private final PlanReplyRepository planReplyRepository;
    // attraction
    private final AttractionRepository attractionRepository;
    private final AttractionImageRepository attractionImageRepository;
    private final AttractionReplyRepository attractionReplyRepository;
    private final RegionRepository regionRepository;

    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;

    @Override
    public AdminDTO.adminPageResponse getAdminManagementList(int item, AdminDTO.adminPageRequestList pageRequestDTO) {
        if (item == 1) { // banner manage
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("bannerIdx").descending());
            Page<Object[]> bannerPage = bannerRepository.getBannerPage(pageRequest);
            Page<AdminDTO.adminBannerList> adminBannerListPage = bannerPage.map(this::bannerPageEntityToDto);
            return new AdminDTO.adminPageResponse<>(adminBannerListPage);
        } else if (item == 2) { // attraction manage
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("attrIdx").ascending());
            Long regionIdx = Long.parseLong(pageRequestDTO.getSearchArea()) + Long.parseLong(pageRequestDTO.getSearchCity());
            RegionEntity region = regionRepository.findByRegionIdx(regionIdx);
            Page<Object[]> adminAttrListPage = attractionRepository.getAttractionPage(region.getArea(), region.getCity(), pageRequest);
            Page<AdminDTO.adminAttrList> attrListPage = adminAttrListPage.map(this::attrPageEntityToDto);
            return new AdminDTO.adminPageResponse<>(attrListPage);
        } else if (item == 3) { // report manage
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("blameIdx").descending());
            Page<Object[]> blamePage = blameRepository.getBlamePage(pageRequest);
            Page<AdminDTO.adminBlameList> blameListPage = blamePage.map(this::blamePageEntityToDto);
            return new AdminDTO.adminPageResponse<>(blameListPage);
        } else { // 문의 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("csIdx").descending());
            Page<Object[]> csPage = csRepository.getCsPage(pageRequest);
            Page<AdminDTO.adminCsList> csListPage = csPage.map(this::csPageEntityToDto);
            return new AdminDTO.adminPageResponse<>(csListPage);
        }
    }

    @Override
    public String deleteAdminItem(int item, long idx) {
        String returnMsg = "";
        if (item == 1) { // 배너 이미지
            BannerEntity banner = bannerRepository.findByBannerIdx(idx);
            // 로컬에서 이미지 삭제
            File file = new File(banner.getPath());
            if (file.exists()) {
                if (file.delete()) returnMsg = "データを削除しました。";
                else returnMsg = "データの削除に失敗しました。";
            } else returnMsg = "データの削除に失敗しました。";
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
                        returnMsg = "データを削除しました。";
                    }
                    if (folderList.length == 0 && folder.isDirectory()) {
                        folder.delete();
                        returnMsg = "フォルダを削除しました。";
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
            returnMsg = "データを削除しました。";
        } else if (item == 4) { // 문의 관리
            csRepository.deleteByCsIdx(idx);
            returnMsg = "データを削除しました。";
        }
        return returnMsg;
    }

    @Override
    public String uploadBannerImage(MultipartFile uploadFile) {
        if (!uploadFile.getContentType().startsWith("image")) return "fail";
        String fileName = uploadFile.getOriginalFilename();
        String folderPath = makeForder("banner");
        String saveName = uploadPath + File.separator + folderPath + File.separator + fileName;
        Path path = Paths.get(saveName);
        try {
            uploadFile.transferTo(path);
            bannerRepository.save(BannerEntity.builder().path(saveName).build());
        } catch (IOException e) {
            return "バナーイメージの追加に失敗しました。";
        }
        return "バナーイメージを追加しました。";
    }

    @Override
    public String craeteAttraction(AdminDTO.attrCreateModifyRequestList requestDTO) {
        long tempAttrIdx = 0L;
        long tempAttrImgIdx = 0L;

        try {
            // requestDTO로 RegionEntity를 가져온다
            RegionEntity region = regionRepository.findRegionEntityByAreaAndCity(requestDTO.getArea(), requestDTO.getCity());
            // idx 생성을 위해 기존 정보를 가져온다.
            List<AttractionEntity> attrList = attractionRepository.findAllByRegionEntity_RegionIdx(region.getRegionIdx());
            long attractionOffset = Long.parseLong(String.valueOf(attrList.get(attrList.size() - 1).getAttrIdx()).substring(2)) + 1;
            tempAttrIdx = Long.parseLong(String.valueOf(region.getRegionIdx()).concat(String.valueOf(attractionOffset)));
            // Entity 생성
            AttractionEntity attraction = AttractionEntity.builder()
                    .attrIdx(tempAttrIdx)
                    .address(requestDTO.getAddress())
                    .description(requestDTO.getDescription())
                    .latitude(requestDTO.getLatitude())
                    .longitude(requestDTO.getLongitude())
                    .attraction(requestDTO.getAttraction())
                    .tag(requestDTO.getTag())
                    .regionEntity(region)
                    .build();
            attractionRepository.save(attraction);
            // 이미지 추가
            if (requestDTO.getImages().get(0) != null) {
                int imageIdx = 1;
                for (MultipartFile createImage : requestDTO.getImages()) {
                    if (!createImage.getContentType().startsWith("image")) break;
                    // 경로 지정
                    String fileName = createImage.getOriginalFilename();
                    String folderPath = makeForder("attraction/" + attraction.getAttrIdx());
                    String save = uploadPath + File.separator + folderPath + File.separator + fileName;
                    Path imageSavePath = Paths.get(save);

                    try {
                        createImage.transferTo(imageSavePath);
                    } catch (IOException e) {
                        attractionRepository.deleteByAttrIdx(tempAttrIdx);
                    }
                    // DB에 추가
                    AttractionImageEntity attractionImage = dtoToEntity(
                            imageSavePath,
                            requestDTO.getThumbnail(),
                            attraction,
                            createImage,
                            Long.valueOf(String.valueOf(region.getRegionIdx()).concat(String.valueOf(attractionOffset).concat(String.valueOf(imageIdx++))))
                    );
                    tempAttrImgIdx = attractionImage.getAttrImgIdx();
                    attractionImageRepository.save(attractionImage);
                }
            }
        } catch (Exception e) {
            attractionRepository.deleteByAttrIdx(tempAttrIdx);
            attractionImageRepository.deleteByAttrImgIdx(tempAttrImgIdx);
            return "登録に失敗しました。";
        }
        return "登録しました。";
    }

    @Override
    public AdminDTO.attrModifyDataResponse getModifyAttractionData(long attrIdx) {
        List<AttractionImageEntity> attraction = attractionImageRepository.findAllByAttrEntityAttrIdx(attrIdx);
        return attrModiftDataEntityToDto(attraction);
    }

    @Override
    public String modifyAttractionData(AdminDTO.attrCreateModifyRequestList requestDTO) {
        String returnMsg = "";
        RegionEntity region = regionRepository.findRegionEntityByAreaAndCity(requestDTO.getArea(), requestDTO.getCity());
        AttractionEntity originalAttraction = attractionRepository.findByAttrIdx(requestDTO.getAttrIdx());
        AttractionEntity newAttraction = dtoToEntity(originalAttraction, requestDTO, region);
        attractionRepository.patchModifyAttraction(newAttraction);
        List<AttractionImageEntity> attractionImg = attractionImageRepository.findAllByAttrEntityAttrIdx(newAttraction.getAttrIdx());
        int attrImgSize = attractionImg.size();

        // 삭제할 이미지 검사
        if (!requestDTO.getRmImg().isEmpty()) {
            String[] removeImages = requestDTO.getRmImg().split(",");
            for (String rmImage : removeImages) {
                AttractionImageEntity findImgData = attractionImageRepository.findByAttrImgIdx(Long.parseLong(rmImage.replaceAll("[^0-9]", "")));
                attractionImageRepository.deleteByAttrImgIdx(Long.parseLong(rmImage.replaceAll("[^0-9]", "")));
                // 저장소에서 이미지 삭제
                File file = new File(findImgData.getPath());
                if (file.exists()) {
                    if (file.delete()) returnMsg = "データを削除しました。";
                    else returnMsg = "データの削除に失敗しました。";
                } else returnMsg = "データの削除に失敗しました。";
            }
            String newAttrIdxBase = String.valueOf(requestDTO.getAttrIdx());
            for (int offset = 0; offset < attrImgSize; offset++) {
                attractionRepository.patchModifyAttrImg(attractionImg.get(offset), requestDTO.getAttrIdx(), Long.parseLong(newAttrIdxBase.concat(String.valueOf(offset + 1))));
            }
        }

        // 추가한 이미지가 있으면 추가
        if (requestDTO.getImages().get(0) != null) {
            for (MultipartFile uploadFile : requestDTO.getImages()) {
                if (!uploadFile.getContentType().startsWith("image")) {
                    break;
                }
                String fileName = uploadFile.getOriginalFilename();
                String folderPath = makeForder("attraction/" + newAttraction.getAttrIdx());
                String save = uploadPath + File.separator + folderPath + File.separator + fileName;
                Path imageSavePath = Paths.get(save);

                // 중복 검사
                AttractionImageEntity checkFile = attractionImageRepository.findByPath(imageSavePath.toString());
                if (checkFile == null) {
                    try {
                        uploadFile.transferTo(imageSavePath);
                    } catch (IOException e) {
                        returnMsg = "イメージの保存に失敗しました。";
                    }
                    AttractionImageEntity attractionImageEntity = dtoToEntity(
                            imageSavePath,
                            requestDTO.getThumbnail(),
                            newAttraction,
                            uploadFile,
                            Long.valueOf(String.valueOf(requestDTO.getAttrIdx()).concat(String.valueOf(++attrImgSize)))
                    );
                    attractionImageRepository.save(attractionImageEntity);
                } else returnMsg = "イメージが重複しています。";
            }
        }
        // 메인 이미지만 변경
        attractionImg = attractionImageRepository.findAllByAttrEntityAttrIdx(newAttraction.getAttrIdx());
        for (AttractionImageEntity offset : attractionImg) {
            String folderPath = makeForder("attraction/" + newAttraction.getAttrIdx());
            String save = uploadPath + File.separator + folderPath + File.separator + requestDTO.getThumbnail();
            attractionRepository.patchModifyAttrMainImg(save, offset.getAttrImgIdx());
        }
        return returnMsg;
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

    @Override
    public AdminDTO.csViewerResponse getCSViewer(long csIdx) {
        CsEntity cs = csRepository.findByCsIdx(csIdx);
        return entityToDto(cs);
    }

    @Override
    public String insertCsReply(long csIdx, AdminDTO.CsReplyRequest csReplyRequest) {
        try {
            csRepository.patchCsReply(csIdx, csReplyRequest.getReply());
        } catch (Exception e) {
            return "登録失敗";
        }
        return "登録成功";
    }

    @Override
    public void blockCron() {
        Calendar calendar = Calendar.getInstance();
        List<BlockEntity> blockList = blockRepository.findAll();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String timeNow = simpleDateFormat.format(new java.util.Date(calendar.getTimeInMillis()));
        String blockTime = "";

        for (BlockEntity block : blockList) {
            blockTime = simpleDateFormat.format(block.getEndDate());
            // 차단 종료 일자와 현재 시간이 동일하거나 지났을때
            if (blockTime.compareTo(timeNow) < 1) {
                UsersEntity user = usersRepository.findByUserIdx(block.getUsersEntity().getUserIdx());
                // 유저 테이블에서 블락 0 으로 변경
                usersRepository.patchUsersBlockCondition(user, 1); // 차단 해제
                java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis()); // 시간 리셋
                BlockEntity newBlock = BlockEntity.builder()
                        .count(block.getCount())
                        .endDate(endDate)
                        .usersEntity(user)
                        .build();
                blockRepository.patchBlockManager(newBlock); // 해제
            }
        }
    }

    @Override
    public AdminDTO.blameViewerResponse getBlameViewerData(long blameIdx) {
        BlameEntity blame = blameRepository.findByBlameIdx(blameIdx);
        return entityToDto(blame);
    }


    @Override
    public String userBlockManager(long blameIdx, AdminDTO.userBlockRequest userBlockRequest) {
        BlockEntity originalBlock = blockRepository.findByUsersEntity_UserIdx(userBlockRequest.getUserIdx());
        Calendar calendar = Calendar.getInstance();
        // 0 = 차단 / 1 = 해제
        if (userBlockRequest.getFlag() == 0) {
            BlameEntity blame = blameRepository.findByBlameIdx(blameIdx);
            // UserIdx 검사
            if (blame.getUsersEntity().getUserIdx().equals(userBlockRequest.getUserIdx())) {
                // 유저 차단
                usersRepository.patchUsersBlockCondition(blame.getUsersEntity(), 0);
                if (originalBlock == null) { // 첫 차단
                    calendar.add(Calendar.DATE, 3);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = makeBlockEntity((byte) 1, endDate, blame.getUsersEntity());
                    blockRepository.save(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequest); // 컨텐츠 삭제
                } else if (originalBlock.getCount() == 1) { // 두 번째 차단
                    calendar.add(Calendar.DATE, 7);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = makeBlockEntity((byte) 2, endDate, blame.getUsersEntity());
                    blockRepository.patchBlockManager(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequest); // 컨텐츠 삭제
                } else if (originalBlock.getCount() == 2)  { // 세 번째 차단 (영구 차단)
                    calendar.add(Calendar.YEAR, 100);
                    java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis());
                    BlockEntity newBlock = makeBlockEntity((byte) 3, endDate, blame.getUsersEntity());
                    blockRepository.patchBlockManager(newBlock); // 차단
                    blockAndDelete(blame, userBlockRequest); // 컨텐츠 삭제
                } else {
                    return "処理失敗";
                }
            } else return "処理失敗";
            return "処理完了";
        } else {
            UsersEntity user = usersRepository.findByUserIdx(userBlockRequest.getUserIdx());
            usersRepository.patchUsersBlockCondition(user, 1); // 차단 해제
            // 블락 테이블에서 1회면 삭제
            // 2회 이상은 1 차감
            if (originalBlock.getCount() > 1) { // 차감
                java.util.Date endDate = new java.util.Date(calendar.getTimeInMillis()); // 시간 리셋
                BlockEntity newBlock = makeBlockEntity((byte) (originalBlock.getCount() - 1), endDate, user);
                blockRepository.patchBlockManager(newBlock); // 해제
            } else { // 삭제
                blockRepository.deleteByBlockIdx(originalBlock.getBlockIdx());
            }
            return "処理完了";
        }
    }

    private BlockEntity makeBlockEntity(byte count, java.util.Date endDate, UsersEntity users) {
        return BlockEntity.builder()
                .count(count)
                .endDate(endDate)
                .usersEntity(users)
                .build();
    }

    private void blockAndDelete(BlameEntity blame, AdminDTO.userBlockRequest userBlockRequestDto) {
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
}
