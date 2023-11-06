package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttractionRepositoryCustom {
    Page<Object[]> getAttractionPage(String type, String keyword, Pageable pageable);

    void patchModifyAttraction(AttractionEntity attrEntity);

    void patchModifyAttrImg(AttractionImageEntity attrImg, Long attrIdx, Long attrImgIdx);

    void patchModifyAttrMainImg(String newThumbnails, Long offsetIdx);
}
