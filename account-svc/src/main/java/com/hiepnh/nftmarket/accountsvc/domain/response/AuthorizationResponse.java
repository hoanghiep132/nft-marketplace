package com.hiepnh.nftmarket.accountsvc.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationResponse extends BaseResponse {
    private boolean allow = false;

    public AuthorizationResponse() {
        super();
    }
}
