package com.hiepnh.nftmarket.accountsvc.service;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.domain.request.UpdateUserInformationRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.domain.dto.UserDTO;
import com.hiepnh.nftmarket.accountsvc.entities.UserEntity;

public interface UserService {

    BaseResponse createNewUser(String walletAddress, EnumCommon.WalletType walletType, Long nonce);

    GetSingleItemResponse<UserEntity> findByWalletAddress(String walletAddress, EnumCommon.WalletType walletType);

    GetSingleItemResponse<UserDTO> findByInfoWalletAddress(String walletAddress);

    BaseResponse updateUserInformation(UpdateUserInformationRequest request);
}
