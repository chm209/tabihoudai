package com.tabihoudai.tabihoudai_api.security.service;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.exception.NotFoundUserException;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import com.tabihoudai.tabihoudai_api.security.principal.UsersPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsersDetailServiceCustomImpl implements UsersDetailsServiceCustom {

    private final UsersRepository usersRepository;

    @Override
    public UsersPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = usersRepository.securityFindByEmail(username)
                .orElseThrow(() -> NotFoundUserException.EXCEPTION);
        if(user != null) {
            return new UsersPrincipal(user);
        }
        return null;
    }
}
