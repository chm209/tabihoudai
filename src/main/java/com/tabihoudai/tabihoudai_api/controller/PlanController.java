package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.PlanReplyDTO;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanReplyRepository;
import com.tabihoudai.tabihoudai_api.service.PlanService;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
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
    private final PlanReplyRepository planReplyRepository;
    private final RegionRepository regionRepository;
    private final AttractionRepository attractionRepository;

    @DeleteMapping("/delete/plan")
    public void delete(@RequestParam("planIdx") Long planIdx){
        planRepository.deleteById(planIdx);
    }

    @DeleteMapping("/delete/reply")
    public void deleteReply(@RequestParam Long planReplyIdx) {
        planReplyRepository.deleteByPlanReplyIdx(planReplyIdx);
    }

    @GetMapping("/planview")
    public Optional<PlanEntity> viewPlan(@RequestParam Long planIdx) {
        return planRepository.findById(planIdx);
    }

    @PostMapping("/write/plan")
    public Long writePlan(@RequestBody PlanDTO planDTO){
        return planService.create(planDTO);
    }

    @PostMapping("/write/reply")
    public Long writeReply(@RequestBody PlanReplyDTO planReplyDTO) {

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

    @GetMapping("")
    public PlanDTO getPlan(@RequestParam Long planIdx){
        return planService.planView(planIdx);
    }

    @GetMapping("/image")
    public String[] getPlanImage(@RequestParam List<Long> attrList) {
        String[] imageArr = new String[attrList.size() - 1];
        for(int i = 0; i < attrList.size() - 1; i++){
            imageArr[i] = planRepository.planAttrImage(attrList.get(i));
        }
        return imageArr;
    }
}

