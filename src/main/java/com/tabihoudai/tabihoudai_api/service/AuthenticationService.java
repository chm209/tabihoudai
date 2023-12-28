package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AuthDTO;

public interface AuthenticationService {
    AuthDTO.JsonWebTokenResponse auth(AuthDTO.AuthenticationRequest authRequest);
}
