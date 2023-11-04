package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttrMngRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.CsRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionImageRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServicesImpl implements AdminServices {

    @Autowired
    private final BannerRepository bannerRepository;

    @Autowired
    private final BlameRepository blameRepository;

    @Autowired
    private final CsRepository csRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final AttractionImageRepository attractionImageRepository;

    @Autowired
    private final RegionRepository regionRepository;

    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;

    @Override
    public String craeteAttraction(AttrMngRequestDTO requestDTO) {
        // requestDTO로 RegionEntity를 가져온다
        RegionEntity regionEntity = regionRepository.findRegionEntityByAreaAndCity(requestDTO.getArea(), requestDTO.getCity());
        // idx 생성을 위해 기존 정보를 가져온다.
        List<AttractionEntity> attrList = attractionRepository.findAllByRegionEntity_RegionIdx(regionEntity.getRegionIdx());
        // Entity 생성
        AttractionEntity attrEntity = AttractionEntity.builder()
                .attrIdx(Long.parseLong(String.valueOf(regionEntity.getRegionIdx()).concat(String.valueOf(attrList.size() + 1))))
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

            for(MultipartFile createImage : requestDTO.getImages()) {
                if (createImage.getContentType().startsWith("image") == false) break;
                // 경로 지정
                String fileName = createImage.getOriginalFilename();
                String folderPath = makeForder("attraction/" + attrEntity.getAttrIdx());
                String save = uploadPath + File.separator + folderPath + File.separator + fileName;
                Path imageSavePath = Paths.get(save);

                try {
                    createImage.transferTo(imageSavePath);
                } catch (IOException e) {}
                // DB에 추가
                AttractionImageEntity attractionImageEntity = attrImgDtoToEntity(
                        imageSavePath,
                        requestDTO.getThumbnail(),
                        attrEntity,
                        createImage,
                        Long.valueOf(String.valueOf(requestDTO.getAttrIdx()).concat(String.valueOf(imageIdx++)))
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

        // 이미지 수정
        // 1. 삭제할 이미지의 idx를 가져와서 DB와 저장소에서 삭제한다.
        // 2. attrImgIdx를 새로 할당해준다.
        // 3. 새로 받은 이미지를 저장소와 DB에 저장해준다.

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
        for(AttractionImageEntity offset : attrImage) {
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
            Page<AdminDTO.attrInfo> result = list.map(objects -> attrEntityToDto(objects));
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
