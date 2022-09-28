package com.hiepnh.nftmarket.coresvc.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonHelper {

    private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String convertToJson(Object input) {
        String rs;
        try {
            rs = mapper.writeValueAsString(input);
        } catch (Exception e) {
            rs = null;
            logger.error("Format obj : {}, err : {} ", input, e);
        }
        return rs;
    }

    public static <T> T convertJsonToObject(String json, Class<T> className) {
        T rs;
        try {
            rs = mapper.readValue(json, className);
        } catch (Exception e) {
            rs = null;
            logger.error("Format json : {}, err : {} ", json, e);
        }
        return rs;
    }

    public static <T> List<T> convertJsonToList(String json, Class<T[]> className) {
        if (Strings.isNullOrEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            T[] pp = mapper.readValue(json, className);
            return Arrays.asList(pp);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static <T> T convertMapToObject(Object obj, Class<T> className) {
        T rs;
        try {
            rs = mapper.convertValue(obj, className);
        } catch (Exception e) {
            rs = null;
            logger.error("Format obj : {}, err : {} ", obj, e);
        }
        return rs;
    }

    public static String convertXmlToJson(String xmlStr) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            JsonNode jsonNode = xmlMapper.readTree(xmlStr.getBytes());
            String value = mapper.writeValueAsString(jsonNode);
            return value;
        } catch (IOException e) {
            logger.error("Convert err: ", e);
            return "";
        }
    }
}
