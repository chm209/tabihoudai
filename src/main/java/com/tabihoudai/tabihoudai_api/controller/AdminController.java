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

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServices adminServices;

    // http://localhost:2094/api/admin/list?item=1
    // 1: banner image | 2: 관광 명소 관리 | 3: 신고 관리 | 4: 문의 관리
    // -----------------------
    //  ⌘ Admin 필터 처리
    //  ⌘ cs Viewer
    //  ⌘ blame Viewer
    //  ⌘ user block
    //  ⌘ cs 답변

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

    // http://localhost:2094/api/admin/delete?item=2&idx=1743
    @DeleteMapping(value =  "/delete")
    public ResponseEntity deleteAdminMngList(@RequestParam(value = "item", required = true, defaultValue = "1") int item,
                                             @RequestParam(value = "idx", required = true) long idx) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.deleteAdminItem(item, idx));
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

    // http://localhost:2094/api/admin/attraction/modify
    // form-data는 attrMngRequestDTO에 맞춰서
    @PatchMapping("/attraction/modify")
    public ResponseEntity patchAttraction(AttrMngRequestDTO attrMngRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.patchAttraction(attrMngRequestDTO));
    }

    // http://localhost:2094/api/admin/attraction/create
    @PostMapping("/attraction/create")
    public ResponseEntity<String> createAttraction(AttrMngRequestDTO attrMngRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.craeteAttraction(attrMngRequestDTO));
    }

    // http://localhost:2094/api/admin/blame/viewer?id=293
    @GetMapping("/blame/viewer")
    public ResponseEntity<AdminDTO.blameDetailInfo> getBlameDetailViewer(@RequestParam(value = "id", required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getBlameDetailViewer(id));
    }

    @PostMapping(value =  "/user/manage", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity userBlockManager(@RequestParam(value = "id", required = true) long id,
            @Validated @RequestBody AdminDTO.userBlockRequestDto userBlockRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.userBlockManager(id, userBlockRequestDto));
    }
}