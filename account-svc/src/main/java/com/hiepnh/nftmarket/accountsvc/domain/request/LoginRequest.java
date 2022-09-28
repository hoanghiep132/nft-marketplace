package com.hiepnh.nftmarket.accountsvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.accountsvc.domain.response.LoginResponse;
import lombok.Data;

@Data
public class LoginRequest {
    private String signature;
    private String walletAddress;

    public LoginResponse validate() {
        LoginResponse response = new LoginResponse();
        if (Strings.isNullOrEmpty(signature) || Strings.isNullOrEmpty(walletAddress)) {
            response.setResult(-1, "Yêu cầu đăng nhập không hợp lệ");
            return response;
        }
        return null;
    }

}
