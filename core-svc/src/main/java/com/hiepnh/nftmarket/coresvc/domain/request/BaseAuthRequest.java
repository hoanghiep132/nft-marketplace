package com.hiepnh.nftmarket.coresvc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiepnh.nftmarket.coresvc.common.HeaderInfo;
import lombok.Data;

@Data
public class BaseAuthRequest {
    @JsonIgnore
    protected HeaderInfo info;
}
