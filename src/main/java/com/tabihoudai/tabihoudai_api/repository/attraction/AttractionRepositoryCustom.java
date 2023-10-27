package com.tabihoudai.tabihoudai_api.repository.attraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttractionRepositoryCustom {
    Page<Object[]> getAttractionPage(String type, String keyword, Pageable pageable);
}
