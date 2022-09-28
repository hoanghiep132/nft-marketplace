package com.hiepnh.nftmarket.accountsvc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiepnh.nftmarket.accountsvc.common.HeaderInfo;
import lombok.Data;

@Data
public class BaseAuthRequest {
    @JsonIgnore
    protected HeaderInfo info;
}
