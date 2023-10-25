package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
import com.tabihoudai.tabihoudai_api.service.HomeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/main")
@RequiredArgsConstructor
public class HomeController {

    private final HomeServices homeServices;

    // http://localhost:2094/api/main/banner
    @GetMapping("/banner")
    public ResponseEntity<List<AdminDTO.bannerInfo>> getBanner() {
        List<AdminDTO.bannerInfo> list = homeServices.getBanner();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // http://localhost:2094/api/main/attraction?area=10&city=13
    // http://localhost:2094/api/main/attraction?area=10
    @GetMapping("/attraction")
    public ResponseEntity<List<AttractionDTO.attrPreview>> getAttraction(
            @RequestParam(value = "area", required = true, defaultValue = "10") int area,
            @RequestParam(value = "city", required = false) Integer city) {

        // Main Screen: city == null
        // Region Selector Screen: city != null
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getAttraction(area, city));
    }

    // http://localhost:2094/api/main/board
    @GetMapping("/board")
    public ResponseEntity<List<BoardDTO.recentBoard>> getBoard() {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getBoard());
    }

    // http://localhost:2094/api/main/plan
    // http://localhost:2094/api/main/plan?area=16
    // Plan 테이블 명소 리스트에 없을 경우 404 에러
    @GetMapping("/plan")
    public ResponseEntity<List<PlanDTO.bestPlan>> getPlan(
            @RequestParam(value = "area", required = false) Integer area) {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getBestPlan(area));
    }
}
