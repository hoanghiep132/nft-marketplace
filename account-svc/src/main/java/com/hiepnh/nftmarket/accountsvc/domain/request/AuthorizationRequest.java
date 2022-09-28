package com.hiepnh.nftmarket.accountsvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.accountsvc.domain.response.LoginResponse;
import lombok.Data;

@Data
public class AuthorizationRequest {
    private String userId;
    private String domain;
    private String object;
    private String action;

    // fool validate
    public LoginResponse validate() {
        LoginResponse response = new LoginResponse();
        if (Strings.isNullOrEmpty(userId)) {
            response.setResult(-1, "Vui lòng nhập tên đăng nhập");
            return response;
        }
        if (Strings.isNullOrEmpty(domain)) {
            response.setResult(-1, "Vui lòng nhập tên đăng nhập");
            return response;
        }
        if (Strings.isNullOrEmpty(object)) {
            response.setResult(-1, "Vui lòng nhập tên đăng nhập");
            return response;
        }
        if (Strings.isNullOrEmpty(action)) {
            response.setResult(-1, "Vui lòng nhập mật khẩu");
            return response;
        }
        return null;
    }

}
