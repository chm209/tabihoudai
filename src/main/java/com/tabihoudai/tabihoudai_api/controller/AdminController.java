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
public class AdminController {

    private final AdminServices adminServices;

    @PostMapping(value = "/list", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AdminDTO.adminPageResponse> getAdminManagementList(
            @RequestParam(value = "item", required = true, defaultValue = "1") int item,
            @Validated @RequestBody AdminDTO.adminPageRequestList adminPageRequestList) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getAdminManagementList(item, adminPageRequestList));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteAdminMngList(@RequestParam(value = "item", required = true, defaultValue = "1") int item,
                                             @RequestParam(value = "idx", required = true) long idx) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.deleteAdminItem(item, idx));
    }

    @PostMapping(value = "/banner/upload")
    public ResponseEntity<String> uploadBannerImage(MultipartFile uploadFile) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.uploadBannerImage(uploadFile));
    }

    @PostMapping("/attraction/create")
    public ResponseEntity<String> createAttraction(AdminDTO.attrCreateModifyRequestList attrMngRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.craeteAttraction(attrMngRequestDTO));
    }













    // http://localhost:2094/api/admin/attraction/load?id=1131
    @GetMapping("/attraction/load")
    public ResponseEntity<AdminDTO.attrDetailData> getAttraction(@RequestParam(value = "id", required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getAttrDetailData(id));
    }

    // http://localhost:2094/api/admin/attraction/modify
    // form-data는 attrMngRequestDTO에 맞춰서
    @PatchMapping("/attraction/modify")
    public ResponseEntity patchAttraction(AdminDTO.attrCreateModifyRequestList attrMngRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.patchAttraction(attrMngRequestDTO));
    }

    // http://localhost:2094/api/admin/cs/viewer?id=246
    @GetMapping("/cs/viewer")
    public ResponseEntity<AdminDTO.csDetailInfo> getCsDetailViewer(@RequestParam(value = "id", required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getCsDetailViewer(id));
    }

    // http://localhost:2094/api/admin/cs/reply?id=245
    // { "reply" : "딥변 내용" }
    @PostMapping(value = "/cs/reply", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postCsReply(@RequestParam(value = "id", required = true) long id,
                                              @Validated @RequestBody AdminDTO.CsReplyRequestDto csReplyRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.postCsReply(id, csReplyRequestDto));
    }

    // http://localhost:2094/api/admin/blame/viewer?id=293
    @GetMapping("/blame/viewer")
    public ResponseEntity<AdminDTO.blameDetailInfo> getBlameDetailViewer(@RequestParam(value = "id", required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getBlameDetailViewer(id));
    }

    // http://localhost:2094/api/admin/user/manage?id=0
    /* {
            "userIdx": "fa721489-4a0e-43aa-aba4-b1613384ceec",
            "flag": 1,
            "contentIdx": 0
       } */
    @PostMapping(value = "/user/manage", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> userBlockManager(@RequestParam(value = "id", required = true) long id,
                                                   @Validated @RequestBody AdminDTO.userBlockRequestDto userBlockRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.userBlockManager(id, userBlockRequestDto));
    }

    @GetMapping("/block/cron")
    public void blockCron() {
        adminServices.blockCron();
    }
}