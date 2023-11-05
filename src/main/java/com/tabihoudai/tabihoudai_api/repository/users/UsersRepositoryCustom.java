package com.tabihoudai.tabihoudai_api.repository.users;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;

public interface UsersRepositoryCustom {
    void patchUsersBlockCondition(UsersEntity user);
}
