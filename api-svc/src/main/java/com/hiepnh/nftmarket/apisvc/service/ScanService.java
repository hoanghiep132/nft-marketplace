package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.utils.JsonUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.Map;


@Component
public class ScanService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final static String url = "https://data-seed-prebsc-1-s1.binance.org:8545/";

    public GetSingleItemResponse<Map<String, Object>> callData(Map<String, Object> param){
        GetSingleItemResponse<Map<String, Object>> response = new GetSingleItemResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String paramJson = JsonUtils.convertToJson(param);

        HttpEntity headerEntity = new HttpEntity(paramJson, headers);
        ResponseEntity<Map> rs = restTemplate.exchange(url, HttpMethod.POST, headerEntity, Map.class);
        if(rs.getStatusCodeValue() != 0){
            response.setResult(-1, "");
            return response;
        }

        Map<String, Object> data = rs.getBody();

        response.setItem(data);
        response.setSuccess();
        return response;
    }

}
