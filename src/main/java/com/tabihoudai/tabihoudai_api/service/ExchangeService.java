package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.ExchangeResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeService {

    @Value("${exchange.api.url}")
    private String authUrl;

    @Value("${exchange.api.key}")
    private String authKey;

    @Value("${exchange.api.data}")
    private String data;

    public List<String> getJapaneseYenExchangeRate() {
        String searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String urlWithParams =
                String.format(("%s?authkey=%s&searchdate=%s&data=%s"), authUrl, authKey, searchDate, data);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ExchangeResponseDto[] responseDtoArray = restTemplate.getForObject(urlWithParams, ExchangeResponseDto[].class);

            if (responseDtoArray != null) {
                return Arrays.stream(responseDtoArray)
                        .filter(ExchangeResponseDto::isJapaneseYen)
                        .map(ExchangeResponseDto::getKftcDealBasR)
                        .collect(Collectors.toList());
            } else {
                return List.of();
            }

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
