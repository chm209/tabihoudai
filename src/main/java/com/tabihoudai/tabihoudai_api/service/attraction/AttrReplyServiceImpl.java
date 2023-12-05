package com.tabihoudai.tabihoudai_api.service.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.*;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.UserEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttrReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttrReplyServiceImpl implements AttrReplyService{

    private final AttrReplyRepository attrReplyRepository;

    private final UserRepository userRepository;

    private final String PATH="C:\\Users\\dhses\\tabihoudai\\tabi_front\\src\\assets\\images\\attrReply";

    @Override
    @Transactional
    public ReplyListDTO replyRegister(AttrReplyDto attrReplyDto, MultipartFile multipartFile,ReplyRequestDTO replyRequestDTO) {
        if(multipartFile!=null) {
            String fileName="";
            fileName = fileNaming(multipartFile);
            attrReplyDto.setPath(fileName);
        }
        AttrReplyEntity entity = dtoToEntityReply(attrReplyDto);

        try {
            AttrReplyEntity save = attrReplyRepository.save(entity);
            replyRequestDTO.setAttrIdx(save.getAttrIdx().getAttrIdx());
            return getReply(replyRequestDTO);
        } catch (Exception e){
            return null;
        }

    }

    @Override
    public ReplyListDTO replyDelete(AttrReplyDto attrReplyDto,ReplyRequestDTO replyRequestDTO) {
        ReplyListDTO result = new ReplyListDTO();
        boolean delete = false;
        try {
            delete=imageDelete(attrReplyDto);
            if (delete) {
                attrReplyRepository.deleteById(attrReplyDto.getAttrReplyIdx());
                replyRequestDTO.setAttrIdx(attrReplyDto.getAttrIdx());
                result = getReply(replyRequestDTO);
            }
            return result;
        } catch (Exception e){
            return result;
        }

    }

    @Override
    public ReplyListDTO getReply(ReplyRequestDTO replyRequestDTO) {
        Sort sort;
        if (replyRequestDTO.isAsc()){
            sort = Sort.by(replyRequestDTO.getSort()).ascending();
        } else {
            sort = Sort.by(replyRequestDTO.getSort()).descending();
        }
        Pageable request = replyRequestDTO.getPageable(sort);

        AttractionEntity attraction = AttractionEntity.builder().attrIdx(replyRequestDTO.getAttrIdx()).build();
        Slice<AttrReplyEntity> attrReplyEntitySlice = attrReplyRepository.findAllByAttrIdx(attraction, request);
        List<ReplySearchDTO> replyList = new ArrayList<>();

        attrReplyEntitySlice.getContent().forEach(reply -> {
            UserEntity user = userRepository.findAllByUserIdx(reply.getUserIdx().getUserIdx());
            String userEmail = user.getEmail();
            String userImage = user.getProfile();
            String[] userRename = userEmail.split("@");
            replyList.add(reEntityToReplyDto(reply,userRename[0],userImage));
        });
        ReplyListDTO replyListDto = ReplyListDTO.builder().list(replyList).next(attrReplyEntitySlice.hasNext()).build();
        return replyListDto;
    }

    @Override
    public ReplyListDTO replyUpdate(AttrReplyDto attrReplyDto, MultipartFile multipartFile, ReplyRequestDTO replyRequestDTO) {
        ReplyListDTO result = new ReplyListDTO();
        try {
            if (multipartFile==null) {

            } else {

            }
            return result;

        } catch (Exception e){
            return result;
        }

    }

    @Override
    public ReplySearchDTO getReplyOne(Long attrReplyIdx) {
        AttrReplyEntity reply = attrReplyRepository.findByAttrReplyIdx(attrReplyIdx);
        UserEntity user = userRepository.findAllByUserIdx(reply.getUserIdx().getUserIdx());
        ReplySearchDTO replySearchDTO = reEntityToReplyDto(reply, user.getEmail(), user.getProfile());

        return replySearchDTO;
    }

    private String fileNaming(MultipartFile multipartFile){
        String str = PATH;
        String folderPath = str.replace("\\", File.separator);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-m-ss"));
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
        return fileName;
    }

    private boolean imageDelete(AttrReplyDto attrReplyDto){
        boolean delete=false;
        String path = attrReplyRepository.findByAttrReplyIdx(attrReplyDto.getAttrReplyIdx()).getPath();
        String folderPath = PATH.replace("\\", File.separator);
        File file = new File(folderPath+File.separator+path);
        if (file.exists()) {
            delete = file.delete();
        }
        return delete;
    }

}
