package com.tabihoudai.tabihoudai_api.api.plan;

import com.tabihoudai.tabihoudai_api.application.plan.PlanService;
import com.tabihoudai.tabihoudai_api.dao.plan.PlanRepository;
import com.tabihoudai.tabihoudai_api.dto.plan.PlanDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/planview")
@RequiredArgsConstructor
public class PlanViewController {


    private final PlanRepository planRepository;

    private final PlanService planService;

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
