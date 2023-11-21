package com.tabihoudai.tabihoudai_api.api.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ImageController {

    @ResponseBody
    @PostMapping("/upload")
    public Map<String, Object> uploadImage(@RequestParam Map<String, Object> paramMap, MultipartRequest request) throws Exception {
        MultipartFile uploadFile = request.getFile("upload");
        String uploadDir = "C:\\tabi_front\\tabihoudai_front\\src\\assets\\images\\";
        uploadDir.replace("\\", "/");
        String uploadId = UUID.randomUUID().toString() + "." + getExtensionByStringHandling(uploadFile.getOriginalFilename()).get();
        uploadFile.transferTo(new File(uploadDir + uploadId));
        paramMap.put("url", "/src/assets/images/" + uploadId);
        log.info(paramMap.toString());
        return paramMap;
    }
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


}
