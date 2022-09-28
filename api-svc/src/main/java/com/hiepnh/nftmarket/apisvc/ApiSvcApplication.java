package com.hiepnh.nftmarket.apisvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.TimeZone;

@SpringBootApplication
public class ApiSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSvcApplication.class, args);
    }

    @PostConstruct
    public void init() {
        setDefaultTimeZone();
    }

    private void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
}
