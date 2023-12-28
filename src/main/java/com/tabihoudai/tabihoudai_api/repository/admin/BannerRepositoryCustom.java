package com.tabihoudai.tabihoudai_api.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BannerRepositoryCustom {
    Page<Object[]> getBannerPage(Pageable pageable);
}
