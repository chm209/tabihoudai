package com.tabihoudai.tabihoudai_api.service.auth;

import com.tabihoudai.tabihoudai_api.dto.request.AuthenticationRequest;
import com.tabihoudai.tabihoudai_api.dto.response.JsonWebTokenResponse;

public interface AuthenticationService {
    JsonWebTokenResponse auth(AuthenticationRequest authRequest);
}
