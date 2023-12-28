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

    @GetMapping("/banner")
    public ResponseEntity<AdminDTO.bannerInfoResponse> getBanner() {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getBanner());
    }

    @GetMapping("/attraction")
    public ResponseEntity<AttractionDTO.attractionInfoResponse> getAttraction(
            @RequestParam(value = "area", required = true, defaultValue = "10") int area,
            @RequestParam(value = "city", required = false) Integer city) {
        // Main Screen: city == null
        // Region Selector Screen: city != null
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getAttraction(area, city));
    }

    @GetMapping("/plan")
    public ResponseEntity<PlanDTO.planInfoResponse> getPlan(
            @RequestParam(value = "area", required = false) Integer area) {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getPlan(area));
    }

    @GetMapping("/board")
    public ResponseEntity<BoardDTO.boardInfoResponse> getBoard() {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getBoard());
    }

    @GetMapping("/city/list")
    public ResponseEntity<AttractionDTO.regionCityInfoResponse> getCity(
            @RequestParam(value = "area", required = false) Integer area) {
        return ResponseEntity.status(HttpStatus.OK).body(homeServices.getCity(area));
    }
}