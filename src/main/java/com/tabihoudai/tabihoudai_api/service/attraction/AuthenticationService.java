package com.tabihoudai.tabihoudai_api.service.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AuthRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AuthResponseDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.UserDto;

public interface AuthenticationService {
    AuthResponseDTO register(UserDto request);

    AuthResponseDTO authenticate(AuthRequestDTO request);
}
