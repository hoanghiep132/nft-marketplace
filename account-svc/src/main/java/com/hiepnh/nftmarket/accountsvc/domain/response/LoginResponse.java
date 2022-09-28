package com.hiepnh.nftmarket.accountsvc.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class LoginResponse extends BaseResponse {

    @JsonProperty("token")
    private String accessToken;
    private String walletAddress;
    private EnumCommon.WalletType walletType;
    public LoginResponse() {
        super();
    }

}
