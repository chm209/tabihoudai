package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.*;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
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
import org.springframework.data.domain.Sort;
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
    private final PlanLikeRepository planLikeRepository;
    private final BlameRepository blameRepository;

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
        PlanDTO planDTO = planService.planView(planIdx);
        planDTO.setLikeCount(planLikeRepository.countLike(planIdx));
        return planDTO;
    }

    @GetMapping("/view/reply")
    public Page<PlanReplyDTO> getReply(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam Long planIdx) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return planReplyService.planReplyView(pageRequest, planIdx);
    }

    @GetMapping("/image")
    public String[] getPlanImage(@RequestParam List<Long> attrList) {
        String[] imageArr = new String[attrList.size() - 1];
        for(int i = 0; i < attrList.size() - 1; i++){
            imageArr[i] = planRepository.planAttrImage(attrList.get(i));
        }
        return imageArr;
    }

    @GetMapping("/paging/sort")
    public Page<PlanPagingDTO> planPagingSort(@RequestParam("page") int page, @RequestParam("size") int size,
                                          @RequestParam("sortBy") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return planService.orderedPlanList(pageRequest);
    }

    @GetMapping("/search")
    public Page<PlanPagingDTO> planSearch(@RequestParam("page") int page, @RequestParam("size") int size,
                                          @RequestParam("sortBy") String sortBy, @RequestParam("keyword") String keyword) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return planService.searchPlan(pageRequest, keyword);
    }

    @PostMapping("/report")
    public void reportPlan(@RequestBody BlameDTO blameDTO){
        BlameEntity blameEntity = blameDTO.blameDtoTOEntity(blameDTO.getCategory());
        blameRepository.save(blameEntity);
    }

    @GetMapping("/alert")
    //댓글 내용, 작성자, 작성시간, 제목 순
    public Object[] planReplyAlert(@RequestParam String userIdx){
        return planReplyRepository.isRead(userIdx);
    }
}

