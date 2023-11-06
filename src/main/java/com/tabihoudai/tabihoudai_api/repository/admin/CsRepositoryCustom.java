package com.tabihoudai.tabihoudai_api.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CsRepositoryCustom {
    Page<Object[]> getCsPage(Pageable pageable);
    void patchCsReply(long csIdx, String reply);
}
