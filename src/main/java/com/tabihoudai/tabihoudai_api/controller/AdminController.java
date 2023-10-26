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
    // ~ 남은 항목
    // ✗ 관광 명소 관리 리스트
    // ✗ 신고 관리 리스트
    // ✗ 문의 관리 리스트
    @PostMapping(value =  "/list", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAdminManagementList(
            @RequestParam(value = "item", required = true, defaultValue = "1") int item,
            @Validated @RequestBody PageRequestDTO pageRequestDTO ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServices.getAdminManagementList(item, pageRequestDTO));
    }
}