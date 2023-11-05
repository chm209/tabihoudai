package com.tabihoudai.tabihoudai_api.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CsRepositoryCustom {
    Page<Object[]> getCsPage(Pageable pageable);
    void patchCs(long csIdx, String reply);
}
