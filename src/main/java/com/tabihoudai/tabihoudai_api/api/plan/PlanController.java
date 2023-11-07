package com.tabihoudai.tabihoudai_api.api.plan;

import com.tabihoudai.tabihoudai_api.application.plan.PlanService;
import com.tabihoudai.tabihoudai_api.dao.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttractionListDTO;
import com.tabihoudai.tabihoudai_api.dto.plan.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PlanRepository planRepository;
    private final RegionRepository regionRepository;
    private final AttractionRepository attractionRepository;

    @DeleteMapping("/delete/{planIdx}")
    public void delete(@PathVariable("planIdx") Long planIdx){
        planRepository.deleteById(planIdx);
    }

    @GetMapping("/viewplan/{planIdx}")
    public Optional<PlanEntity> viewPlan(@PathVariable Long planIdx) {
        log.info(String.valueOf(planRepository.findById(planIdx)));
        return planRepository.findById(planIdx);
    }

    @PostMapping("/write")
    public Long writePlan(@RequestBody PlanDTO planDTO){
        log.info(planDTO.toString());
        return planService.create(planDTO);
    }

    @GetMapping("/getarea")
    public List<String> getArea() {
        return regionRepository.findArea();
    }

    @GetMapping("/getcity")
    public List<String> getCity(@RequestParam String region) {
        return regionRepository.findCity(region);
    }

    @GetMapping("/getattr")
    public List<Object[]> getAttr(@RequestParam String city) {
        log.info(regionRepository.findRegionIdx(city).toString());
        Long regionIdx = regionRepository.findRegionIdx(city);

        return attractionRepository.getAttractionListByRegionIdx(regionIdx);
    }
}

