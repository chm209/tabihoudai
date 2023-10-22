package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.service.HomeServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HomeServices homeServices;

    @GetMapping("/banner")
    public ResponseEntity<List<AdminDTO.bannerInfo>> getBanner() {
        List<AdminDTO.bannerInfo> list = homeServices.getBanner();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
