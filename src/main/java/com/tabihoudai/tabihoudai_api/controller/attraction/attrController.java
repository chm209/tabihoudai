package com.tabihoudai.tabihoudai_api.controller.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.service.attraction.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class attrController {

    private final AttractionService attractionService;

    @GetMapping("/area")
    public ResponseEntity list(@RequestBody AttrRequestDTO attrRequestDTO){
        return ResponseEntity.ok(attractionService.getAttrList(attrRequestDTO));
    }
}
