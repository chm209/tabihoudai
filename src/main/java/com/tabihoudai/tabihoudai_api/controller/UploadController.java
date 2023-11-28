package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.UploadResultDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {
    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(
            MultipartFile[] uploadFiles
    ) {

        List<UploadResultDTO> resultDTOList
                = new ArrayList<>();

        for (MultipartFile uploadFile :
                uploadFiles) {

            if (uploadFile.getContentType().startsWith("image") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String originalFilename = uploadFile.getOriginalFilename();
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            log.info("filename : {}", fileName);

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName =
                    uploadPath + File.separator +
                            folderPath + File.separator +
                            uuid + "_" + fileName;

            Path path = Paths.get(saveName);
            try {

                uploadFile.transferTo(path);

                String thumbnailSaveName =
                        uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(path.toFile(), thumbnailFile, 100, 100);

                resultDTOList.add(
                        new UploadResultDTO(fileName, uuid, folderPath)
                );
            } catch (IOException e) {
            }
        }
        return ResponseEntity.ok(resultDTOList);
    }
    private String makeFolder() {
        String str = LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        );
        String folderPath = str.replace("//", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
