package com.hiepnh.nftmarket.accountsvc.domain.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHelper {

    private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String convertToJson(Object input) {
        String rs;
        try {
            rs = mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            rs = "";
            logger.error("Format obj : {}, err : {} ", input, e);
        }
        return rs;
    }
}
