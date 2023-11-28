package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.UsersDTO;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import com.tabihoudai.tabihoudai_api.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/api/editprofile")
@RequiredArgsConstructor
public class ProfileEditController {

    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @DeleteMapping("/withdrawal/{userIdx}")
    public void withdrawal(@PathVariable String userIdx) {
        String uuid;
        uuid = strToUUID(userIdx, "-", 8);
        uuid = strToUUID(uuid, "-", 13);
        uuid = strToUUID(uuid, "-", 18);
        uuid = strToUUID(uuid, "-", 23);

        usersRepository.deleteById(UUID.fromString(uuid));
    }

    @PutMapping("")
    public void editProfile(@ModelAttribute UsersDTO usersDTO) throws Exception {
        usersService.editProfile(usersDTO);
    }

    public String strToUUID(String str, String value, int idx){
        return str.substring(0, idx) + value + str.substring(idx);
    }
}
