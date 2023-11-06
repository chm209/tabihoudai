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
    public ResponseEntity<String> createAttraction(AdminDTO.attrCreateModifyRequestList attrCreateModifyRequestList) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.craeteAttraction(attrCreateModifyRequestList));
    }

    @GetMapping("/attraction/load")
    public ResponseEntity<AdminDTO.attrModifyDataResponse> getModifyAttractionData(@RequestParam(value = "idx", required = true) long idx) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getModifyAttractionData(idx));
    }

    @PatchMapping("/attraction/modify")
    public ResponseEntity modifyAttractionData(AdminDTO.attrCreateModifyRequestList attrCreateModifyRequestList) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.modifyAttractionData(attrCreateModifyRequestList));
    }

    @GetMapping("/cs/viewer")
    public ResponseEntity<AdminDTO.csViewerResponse> getCSViewer(@RequestParam(value = "idx", required = true) long idx) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getCSViewer(idx));
    }

    @PostMapping(value = "/cs/reply", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> insertCsReply(@RequestParam(value = "idx", required = true) long idx,
                                              @Validated @RequestBody AdminDTO.CsReplyRequest csReplyRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.insertCsReply(idx, csReplyRequest));
    }

    @GetMapping("/block/cron")
    public void blockCron() {
        adminServices.blockCron();
    }

    @GetMapping("/blame/viewer")
    public ResponseEntity<AdminDTO.blameViewerResponse> getBlameViewerData(@RequestParam(value = "idx", required = true) long idx) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getBlameViewerData(idx));
    }

    @PostMapping(value = "/user/manage", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> userBlockManager(@RequestParam(value = "idx", required = true) long idx,
                                                   @Validated @RequestBody AdminDTO.userBlockRequest userBlockRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.userBlockManager(idx, userBlockRequest));
    }
}