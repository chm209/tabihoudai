package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.controller.ImageController;
import com.tabihoudai.tabihoudai_api.dto.UsersDTO;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ImageController imageController;

    @Transactional
    public void editProfile(UsersDTO usersDTO) throws Exception {
        UsersEntity usersEntity = usersDTO.usersDtoToEntity();
        deletePreviousProfileImage(usersEntity);
        usersEntity.setProfile(uploadProfileImage(usersDTO.getImage()));
        usersRepository.editUsersInfo(usersEntity);
    }

    public String uploadProfileImage(MultipartFile multipartFile) throws Exception {
        String uploadDir = "C:\\tabi_front\\tabihoudai_front\\src\\assets\\images\\profile\\";
        uploadDir.replace("\\", "/");
        String uploadId = UUID.randomUUID().toString() + "." + imageController.getExtensionByStringHandling(multipartFile.getOriginalFilename()).get();
        multipartFile.transferTo(new File(uploadDir + uploadId));
        return "/src/assets/images/profile/" + uploadId;
    }

    public void deletePreviousProfileImage(UsersEntity usersEntity) {
        String uploadDir = "C:\\tabi_front\\tabihoudai_front" + usersRepository.findByUserIdx(usersEntity.getUserIdx()).getProfile();
        uploadDir.replace("\\", "/");
        Path path = Paths.get(uploadDir);
        try {
            Files.delete(path);
            System.out.println("삭제 시도");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
