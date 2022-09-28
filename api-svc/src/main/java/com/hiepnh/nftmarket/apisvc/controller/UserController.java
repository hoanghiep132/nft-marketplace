package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.domain.request.DetailRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.UserEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.UserDTO;
import com.hiepnh.nftmarket.apisvc.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/user")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/detail")
    public GetSingleItemResponse<UserEntity> detail(@RequestBody @Valid DetailRequest detailRequest) {
        return userService.findUserById(detailRequest.getId());
    }

    @GetMapping("/info/{address}")
    public GetSingleItemResponse<UserDTO> getInfo(@PathVariable("address") String address) {
        return userService.findByInfoWalletAddress(address);
    }
}
