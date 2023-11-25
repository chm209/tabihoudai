package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanLikeRepository;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanReplyRepository;
import com.tabihoudai.tabihoudai_api.service.PlanLikeService;
import com.tabihoudai.tabihoudai_api.service.PlanReplyService;
import com.tabihoudai.tabihoudai_api.service.PlanService;
import com.tabihoudai.tabihoudai_api.repository.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PlanReplyService planReplyService;
    private final PlanLikeService planLikeService;
    private final PlanRepository planRepository;
    private final PlanReplyRepository planReplyRepository;
    private final RegionRepository regionRepository;
    private final AttractionRepository attractionRepository;

    @DeleteMapping("/delete/plan")
    public void deletePlan(@RequestParam("planIdx") Long planIdx){
        planRepository.deleteById(planIdx);
    }

    @DeleteMapping("/delete/reply")
    public void deleteReply(@RequestParam Long planReplyIdx) {
        planReplyRepository.deleteByPlanReplyIdx(planReplyIdx);
    }

    @PostMapping("/write/plan")
    public Long writePlan(@RequestBody PlanDTO planDTO){
        return planService.create(planDTO);
    }

    @PostMapping("/write/reply")
    public Long writeReply(@RequestBody PlanReplyDTO planReplyDTO) {
        return planReplyService.create(planReplyDTO);
    }

    @PutMapping("/edit/plan")
    public Long editPlan(@RequestBody PlanEditDTO planEditDTO){
        return planService.edit(planEditDTO);
    }

    @GetMapping("/chklike")
    public Long chkLike(@RequestBody PlanLikeDTO planLikeDTO){
        return planLikeService.chkLike(planLikeDTO);
    }

    @PostMapping("/addlike")
    public void addLike(@RequestBody PlanLikeDTO planLikeDTO) {
        planLikeService.addLike(planLikeDTO);
    }

    @DeleteMapping("/dislike")
    public void dislike(@RequestBody PlanLikeDTO planLikeDTO) {
        planLikeService.disLike(planLikeDTO);
    }

    @PutMapping("/edit/reply")
    public void editPlanReply(@RequestBody PlanReplyEditDTO planReplyEditDTO) {
        planReplyService.edit(planReplyEditDTO);
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
        Long regionIdx = regionRepository.findRegionIdx(city);
        return attractionRepository.getAttractionListByRegionIdx(regionIdx);
    }

    @GetMapping("/view/plan")
    public PlanDTO getPlan(@RequestParam Long planIdx){
        return planService.planView(planIdx);
    }

    @GetMapping("/view/reply")
    public PlanReplyDTO getReply(@RequestParam Long planIdx) {
        return planReplyService.planReplyView(planIdx);
    }

    @GetMapping("/image")
    public String[] getPlanImage(@RequestParam List<Long> attrList) {
        String[] imageArr = new String[attrList.size() - 1];
        for(int i = 0; i < attrList.size() - 1; i++){
            imageArr[i] = planRepository.planAttrImage(attrList.get(i));
        }
        return imageArr;
    }

    @GetMapping("/paging")
    public Page<PlanPagingDTO> planPaging(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return planService.findAll(pageRequest);
    }
}

