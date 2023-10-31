package com.tabihoudai.tabihoudai_api.controller.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.service.attraction.AttractionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class attrController {

    private final AttractionService attractionService;

    @PostMapping("/area")
    public ResponseEntity list(@RequestBody AttrRequestDTO attrRequestDTO){
        return ResponseEntity.ok(attractionService.getAttrList(attrRequestDTO));
    }

    @GetMapping("/detail/{idx}")
    public ResponseEntity detail(@PathVariable("idx") Long idx){
        return ResponseEntity.ok(attractionService.getAttrDetail(idx));
    }

    @PostMapping("/detail/registration")
    public ResponseEntity replyRegist(){
        return null;
    }
}
