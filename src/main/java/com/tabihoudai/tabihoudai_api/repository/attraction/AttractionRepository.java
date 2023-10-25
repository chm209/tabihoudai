package com.tabihoudai.tabihoudai_api.repository.attraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttractionRepository  {

    Page<Object[]> getAttractionList(Pageable pageable,String area,String word);

}
