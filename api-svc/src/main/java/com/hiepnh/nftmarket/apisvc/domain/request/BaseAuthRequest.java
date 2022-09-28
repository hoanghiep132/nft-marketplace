package com.hiepnh.nftmarket.apisvc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import lombok.Data;

@Data
public class BaseAuthRequest {
    @JsonIgnore
    protected HeaderInfo info;
}
