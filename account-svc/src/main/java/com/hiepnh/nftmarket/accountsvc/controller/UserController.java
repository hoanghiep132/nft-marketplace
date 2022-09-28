package com.hiepnh.nftmarket.accountsvc.controller;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.common.HeaderInfo;
import com.hiepnh.nftmarket.accountsvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.accountsvc.domain.request.UpdateUserInformationRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.entities.UserEntity;
import com.hiepnh.nftmarket.accountsvc.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/detail")
    public GetSingleItemResponse<UserEntity> detail(@RequestHeader Map<String, String> headers) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        return userService.findByWalletAddress(headerInfo.getUsername(), EnumCommon.WalletType.META_MASK);
    }

    @PostMapping("/update")
    public BaseResponse updateUserInfo(@RequestHeader Map<String, String> headers,
                                       @RequestBody UpdateUserInformationRequest request){
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        BaseResponse response = userService.updateUserInformation(request);
        logger.info("updateUser req: {}, resp: {}", request, response);
        return response;
    }
}
