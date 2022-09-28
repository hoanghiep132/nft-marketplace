package com.hiepnh.nftmarket.accountsvc.service;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.domain.request.LoginRequest;
import com.hiepnh.nftmarket.accountsvc.domain.request.LogoutRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.LoginResponse;

public interface AuthenticationService {

    GetSingleItemResponse<String> loginRequest(String walletAddress);

    LoginResponse login(LoginRequest request);

    BaseResponse logout(LogoutRequest request);
}
