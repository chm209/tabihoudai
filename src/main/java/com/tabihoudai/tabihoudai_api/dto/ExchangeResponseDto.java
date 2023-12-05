package com.tabihoudai.tabihoudai_api.dto;

import com.tabihoudai.tabihoudai_api.controller.ExchangeController;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Getter
@NoArgsConstructor
public class ExchangeResponseDto {
    private String cur_unit;
    private String kftc_bkpr;
    private String kftc_deal_bas_r;
    private String cur_nm;

    public static ParameterizedTypeReference<List<ExchangeResponseDto>> getListParameterizedTypeReference() {
        return new ParameterizedTypeReference<List<ExchangeResponseDto>>() {};
    }

    public boolean isJapaneseYen() {
        return "JPY(100)".equals(cur_unit);
    }
}
