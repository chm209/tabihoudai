package com.tabihoudai.tabihoudai_api.security.service;

import com.tabihoudai.tabihoudai_api.security.principal.UsersPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService extends UserDetailsService {

    UsersPrincipal loadUserByUsername(String username) throws UsernameNotFoundException;

}
