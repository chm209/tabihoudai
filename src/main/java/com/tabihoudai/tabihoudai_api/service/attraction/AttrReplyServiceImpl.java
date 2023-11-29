package com.tabihoudai.tabihoudai_api.service.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrReplyDto;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.ReplyListDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.ReplySearchDTO;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.UserEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttrReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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

    @Override
    public ReplyListDTO getReply(Long attrIdx, int page, int size) {
        PageRequest request = PageRequest.of(page,size, Sort.by("attrReplyIdx"));

        AttractionEntity attraction = AttractionEntity.builder().attrIdx(attrIdx).build();
        Slice<AttrReplyEntity> attrReplyEntitySlice = attrReplyRepository.findAllByAttrIdx(attraction, request);
        List<ReplySearchDTO> replyList = new ArrayList<>();


        attrReplyEntitySlice.getContent().forEach(reply -> {
            String userEmail = userRepository.findAllByUserIdx(reply.getUserIdx().getUserIdx()).getEmail();
            String[] userRename = userEmail.split("@");
            replyList.add(ReplySearchDTO.builder().
                        content(reply.getContent()).
                        email(userRename[0]).
                        regDate(reply.getRegDate()).
                        score(reply.getScore()).
                        path(reply.getPath()).build());
        });
        ReplyListDTO replyListDto = ReplyListDTO.builder().list(replyList).next(attrReplyEntitySlice.hasNext()).build();
        return replyListDto;
    }


}
