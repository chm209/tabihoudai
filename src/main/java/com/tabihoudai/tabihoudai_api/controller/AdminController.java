package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.service.AdminServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServices adminServices;

    // http://localhost:2094/api/admin/list?item=1
    // 1: banner image | 2: 관광 명소 관리 | 3: 신고 관리 | 4: 문의 관리
    // -----------------------
    // ~ 완료 항목
    //  ⌘ 배너 이미지 리스트 구현 완료
    //  ⌘ 관광 명소 관리 리스트 구현 완료
    //  ⌘ 신고 관리 리스트 구현 완료
    //  ⌘ 문의 관리 리스트 구현 완료
    //  ⌘ 이미지 업로드 / 서버에 배포할때 이미지 저장 경로 바꿔줘야함
    //  ⌘ 명소 추가
    // ~ 남은 항목
    //  ⌘ Admin 필터 처리
    //  ⌘ 명소 수정


    // http://localhost:2094/api/admin/list?item=2
    // 1. 배너 이미지 관리 / 2. 관광 명소 관리 / 3. 신고관리 / 4. 문의 관리
    /* body - JSON
     * { "page" : 2, "size" : 10, "searchArea" : "20", "searchCity" : "1" } // item == 2
     * { "page" : 1, "size" : 10, "searchArea" : "", "searchCity" : "" } // item == 1, 3, 4
     */
    @PostMapping(value =  "/list", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAdminManagementList(
            @RequestParam(value = "item", required = true, defaultValue = "1") int item,
            @Validated @RequestBody PageRequestDTO pageRequestDTO ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getAdminManagementList(item, pageRequestDTO));
    }

    // http://localhost:2094/api/admin/banner/upload
    // form-data: uploadFile, imageFile
    @PostMapping(value =  "/banner/upload")
    public ResponseEntity<String> uploadBannerImage(MultipartFile uploadFile) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.uploadBannerImage(uploadFile));
    }

    // http://localhost:2094/api/admin/attraction/load?id=1131
    @GetMapping("/attraction/load")
    public ResponseEntity<AdminDTO.attrDetailData> getAttraction(@RequestParam(value = "id", required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getAttrDetailData(id));
    }

    // http://localhost:2094/api/admin/attraction/modify?id=1131
    @PatchMapping("/attraction/modify")
    public ResponseEntity patchAttraction(AttrCreateRequestDTO attrCreateRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.patchAttraction(attrCreateRequestDTO));
    }
}