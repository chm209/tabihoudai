package com.tabihoudai.tabihoudai_api.api.plan;

import com.tabihoudai.tabihoudai_api.application.plan.PlanService;
import com.tabihoudai.tabihoudai_api.dao.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.plan.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PlanRepository planRepository;

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
}
