package com.tabihoudai.tabihoudai_api.controller.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrReplyDto;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.ReplyRequestDTO;
import com.tabihoudai.tabihoudai_api.service.attraction.AttrReplyService;
import com.tabihoudai.tabihoudai_api.service.attraction.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/detail/reply")
    public ResponseEntity replyRegister(@RequestPart(value = "attrReplyDto") AttrReplyDto attrReplyDto,
                                        @RequestPart(value = "file",required = false)MultipartFile multipartFile,
                                        @RequestPart(value = "replyRequestDto")ReplyRequestDTO replyRequestDTO){
        return ResponseEntity.ok(attrReplyService.replyRegister(attrReplyDto,multipartFile,replyRequestDTO));
    }

    @DeleteMapping("/detail/reply")
    public ResponseEntity replyRemove(@RequestPart(value = "attrReplyDto") AttrReplyDto attrReplyDto,
                                      @RequestPart(value = "replyRequestDto")ReplyRequestDTO replyRequestDTO){
        return ResponseEntity.ok(attrReplyService.replyDelete(attrReplyDto,replyRequestDTO));
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
                                   @RequestParam("sort") String sort,
                                   @RequestParam("asc") boolean asc){
        return ResponseEntity.ok(attrReplyService.getReply(
                ReplyRequestDTO.builder().attrIdx(attrIdx).page(page).sort(sort).asc(asc).build()));
    }

    @PutMapping("/detail/reply")
    public ResponseEntity replyUpdate(@RequestPart(value = "attrReplyDto") AttrReplyDto attrReplyDto,
                                      @RequestPart(value = "file",required = false)MultipartFile multipartFile,
                                      @RequestPart(value = "replyRequestDto")ReplyRequestDTO replyRequestDTO){

        return ResponseEntity.ok(attrReplyService.replyUpdate(attrReplyDto,multipartFile,replyRequestDTO));
    }

    @GetMapping("/detail/reply/{idx}")
    public ResponseEntity replySearchOne(@PathVariable("idx") Long attrReplyIdx){
        return ResponseEntity.ok(attrReplyService.getReplyOne(attrReplyIdx));
    }

}
