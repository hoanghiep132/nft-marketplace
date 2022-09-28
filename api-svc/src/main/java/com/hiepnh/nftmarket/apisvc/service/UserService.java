package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.UserEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    GetSingleItemResponse<UserEntity> findUserById(Integer id);

    GetSingleItemResponse<UserDTO> findByInfoWalletAddress(String walletAddress);
}
