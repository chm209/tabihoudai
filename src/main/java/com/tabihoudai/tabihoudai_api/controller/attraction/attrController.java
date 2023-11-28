package com.tabihoudai.tabihoudai_api.controller.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrReplyDto;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.service.attraction.AttrReplyService;
import com.tabihoudai.tabihoudai_api.service.attraction.AttractionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Attr;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class attrController {

    private final AttractionService attractionService;

    private final AttrReplyService attrReplyService;

    @PostMapping("/area")
    public ResponseEntity list(@RequestBody AttrRequestDTO attrRequestDTO){
        return ResponseEntity.ok(attractionService.getAttrList(attrRequestDTO));
    }

    @GetMapping("/detail/{idx}")
    public ResponseEntity detail(@PathVariable("idx") Long idx){
        return ResponseEntity.ok(attractionService.getAttrDetail(idx));
    }

    @PostMapping("/detail")
    public ResponseEntity replyRegist(@RequestPart(value = "attrReplyDto") AttrReplyDto attrReplyDto,
                                      @RequestPart(value = "file",required = false)MultipartFile multipartFile){
        return ResponseEntity.ok(attrReplyService.register(attrReplyDto,multipartFile));
    }

    @DeleteMapping("/detail")
    public ResponseEntity replyRemove(@RequestBody AttrReplyDto attrReplyDto){
        return ResponseEntity.ok(attrReplyService.delete(attrReplyDto));
    }

    @GetMapping("/area-list")
    public ResponseEntity areaList(){
        return ResponseEntity.ok(attractionService.getAreaList());
    }
    @GetMapping("/city-list/{area}")
    public ResponseEntity cityList(@PathVariable("area") String area){
        return ResponseEntity.ok(attractionService.getCityList(area));
    }

    @GetMapping("/detail/find")
    public ResponseEntity getReply(@RequestParam("attrIdx") Long attrIdx,
                                   @RequestParam("page") int page,
                                   @RequestParam("size") int size){
        return ResponseEntity.ok(attrReplyService.getReply(attrIdx,page,size));
    }
}
