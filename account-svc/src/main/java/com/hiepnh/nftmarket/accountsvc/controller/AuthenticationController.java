package com.hiepnh.nftmarket.accountsvc.controller;

import com.hiepnh.nftmarket.accountsvc.common.Common;
import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.domain.request.LoginRequest;
import com.hiepnh.nftmarket.accountsvc.domain.request.LogoutRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.LoginResponse;
import com.hiepnh.nftmarket.accountsvc.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/connect-request")
    public GetSingleItemResponse<String> loginRequest(@RequestParam(name = "walletAddress") String walletAddress) {
        return authenticationService.loginRequest(walletAddress);
    }

    @PostMapping("/connect")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        logger.info("=>login req: {}", request);
        LoginResponse response = request.validate();
        if (response == null) {
            response = authenticationService.login(request);
        }
        logger.info("<=login req: {}, resp: {}", request, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout(
            @RequestHeader(Common.USER_ID_IN_HEADER) String username,
            @RequestBody LogoutRequest request) {
        logger.info("=>logout username: {}", username);
        BaseResponse response = authenticationService.logout(request);
        return ResponseEntity.ok(response);
    }

}
