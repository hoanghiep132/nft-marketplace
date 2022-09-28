package com.hiepnh.nftmarket.apisvc.process;

import com.hiepnh.nftmarket.apisvc.utils.AppUtils;
import com.hiepnh.nftmarket.apisvc.utils.JsonUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SenderManager {

    private static SenderManager senderManager = null;

    private RestTemplate restTemplate;

    private SenderManager(){
        restTemplate = new RestTemplate();
    }

    public static synchronized SenderManager getInstance(){
        if(senderManager == null){
            senderManager = new SenderManager();
        }
        return senderManager;
    }

    public String getTransactionByHash(String tnxHash){
        String url = "https://data-seed-prebsc-1-s1.binance.org:8545/";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("jsonrpc", "2.0");
        bodyMap.put("method", "eth_getTransactionByHash");
        bodyMap.put("id", 1);

        List<String> paramList = new ArrayList<>();
        paramList.add(tnxHash);
        bodyMap.put("params", paramList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String bodyJson = JsonUtils.convertToJson(bodyMap);
        HttpEntity headerEntity = new HttpEntity(bodyJson, headers);

        for(int i = 0; i < 5; i++){
            ResponseEntity<Map> rs = restTemplate.exchange(url, HttpMethod.POST, headerEntity, Map.class);
            if(rs.getStatusCodeValue() == 200){
                Map<String, String> dataBody = (Map<String, String>) rs.getBody().get("result");
                String decodedInput = AppUtils.parseString(dataBody.get("input"));

                return decodedInput;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
