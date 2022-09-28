package com.hiepnh.nftmarket.accountsvc.domain.response;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DecodeResponse {

    private int code;
    private String message;
    private String result;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private Map<String, Map<String, String>> customCookies;
    private int exceptionCode;

    public DecodeResponse() {
        headers = new HashMap<>();
        cookies = new HashMap<>();
        customCookies = new HashMap<>();
        exceptionCode = -1;
    }

    public void addHeader(String key, List<String> value) {
        if (value.size() == 1) {
            headers.put(key, value.get(0));
        } else {
            StringBuilder v = new StringBuilder();
            for (String vv : value) {
                if (v.length() > 0) {
                    v.append("; ");
                }
                v.append(vv);
            }
            headers.put(key, v.toString());
        }
        if (key.equalsIgnoreCase("Set-Cookie")) {
            executeCookies(value);
        }
    }

    private void executeCookies(List<String> value) {
        for (String v : value) {
            Map<String, String> map = new HashMap<>();
            String[] str = v.split(";");
            for (String s : str) {
                String[] info = s.trim().split("=");
                if (info.length >= 2) {
                    String key = info[0].trim();
                    if ("path".equalsIgnoreCase(key)) {
                        key = key.toLowerCase();
                    }
                    map.put(key, info[1].trim());
                }
            }
            if (!map.containsKey("path")) {
                map.put("path", "/");
            }
            String path = map.get("path");
            map.remove("path");
            if (customCookies.containsKey(path)) {
                customCookies.get(path).putAll(map);
            } else {
                customCookies.put(path, map);
            }
        }
    }

}
