package com.hiepnh.nftmarket.gateway.config;


import com.hiepnh.nftmarket.gateway.filter.AuthorizationFilter;
import com.hiepnh.nftmarket.gateway.http.HeaderEnhanceFilter;
import com.hiepnh.nftmarket.gateway.repo.SessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public AuthorizationFilter authorizationFilter(HeaderEnhanceFilter headerEnhanceFilter, SessionRepository cachedUserInfoRepo) {
        return new AuthorizationFilter(headerEnhanceFilter, cachedUserInfoRepo);
    }

    @Bean
    public HeaderEnhanceFilter headerEnhanceFilter() {
        return new HeaderEnhanceFilter();
    }

}
