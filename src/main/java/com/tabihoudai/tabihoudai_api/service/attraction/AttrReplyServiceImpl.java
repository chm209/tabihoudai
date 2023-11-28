package com.tabihoudai.tabihoudai_api.service.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrReplyDto;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttrReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttrReplyServiceImpl implements AttrReplyService{

    private final AttrReplyRepository attrReplyRepository;

    @Override
    @Transactional
    public List<AttrReplyDto> register(AttrReplyDto attrReplyDto, MultipartFile multipartFile) {
        String str = "C:\\Users\\dhses\\tabihoudai\\tabi_front\\src\\assets\\images\\attrReply";
        String folderPath = str.replace("\\", File.separator);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-m-ss"));
        if(multipartFile!=null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileName = date + "_" +
                    originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            String saveName = folderPath + File.separator + fileName;
            Path path = Paths.get(saveName);
            try {
                multipartFile.transferTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            attrReplyDto.setPath(fileName);
        }
        AttrReplyEntity entity = dtoToEntityReply(attrReplyDto);

        List<AttrReplyDto> result = new ArrayList<>();
        try {
            AttrReplyEntity save = attrReplyRepository.save(entity);
            List<AttrReplyEntity> replyEntity = attrReplyRepository.getAttractionReply(attrReplyDto.getAttrIdx());
            for (AttrReplyEntity re: replyEntity) {
                result.add(entityToDTOReply(re));
            }
            return result;
        } catch (Exception e){
            return result;
        }

    }

    @Override
    public List<AttrReplyDto> delete(AttrReplyDto attrReplyDto) {
        List<AttrReplyDto> result = new ArrayList<>();
        try {
            attrReplyRepository.deleteById(attrReplyDto.getAttrReplyIdx());
            List<AttrReplyEntity> replyEntity = attrReplyRepository.getAttractionReply(attrReplyDto.getAttrIdx());
            for (AttrReplyEntity re: replyEntity) {
                result.add(entityToDTOReply(re));
            }
            return result;
        } catch (Exception e){
            return result;
        }

    }
}
