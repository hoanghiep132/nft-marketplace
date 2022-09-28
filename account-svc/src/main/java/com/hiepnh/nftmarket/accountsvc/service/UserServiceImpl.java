package com.hiepnh.nftmarket.accountsvc.service;

import com.hiepnh.nftmarket.accountsvc.common.Common;
import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.domain.request.UpdateUserInformationRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.domain.dto.UserDTO;
import com.hiepnh.nftmarket.accountsvc.entities.UserEntity;
import com.hiepnh.nftmarket.accountsvc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseService implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${application.default_avatar}")
    private String defaultAvatar;

    @Override
    public BaseResponse createNewUser(String walletAddress, EnumCommon.WalletType walletType, Long nonce) {
        BaseResponse response = new BaseResponse();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(Common.DEFAULT_USERNAME);
        userEntity.setWalletAddress(walletAddress);
        userEntity.setWalletType(walletType);
        userEntity.setAvatar(defaultAvatar);
        userEntity.setNonce(nonce);
        userEntity.setCreateAt(System.currentTimeMillis());
        userEntity.setCreateBy(walletAddress);

        try {
            userRepository.save(userEntity);
        }catch (Exception ex){
            logger.error("create new user error: ", ex);
            response.setResult(-1, "error");
            return response;
        }

        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<UserEntity> findByWalletAddress(String walletAddress, EnumCommon.WalletType walletType) {
        GetSingleItemResponse<UserEntity> response = new GetSingleItemResponse<>();

        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(walletAddress, walletType);
        if(!userEntityOptional.isPresent()){
            response.setResult(-1, "Not found");
            return response;
        }

        response.setItem(userEntityOptional.get());
        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<UserDTO> findByInfoWalletAddress(String walletAddress) {
        GetSingleItemResponse<UserDTO> response = new GetSingleItemResponse<>();

        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(walletAddress, EnumCommon.WalletType.META_MASK);
        if(!userEntityOptional.isPresent()){
            response.setResult(-1, "Not found");
            return response;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userDTO.getUsername());
        userDTO.setFullName(userDTO.getFullName());
        userDTO.setNickname(userDTO.getNickname());
        userDTO.setWalletAddress(userDTO.getWalletAddress());
        userDTO.setAvatar(userDTO.getAvatar());
        userDTO.setPhoneNumber(userDTO.getPhoneNumber());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setDescription(userDTO.getDescription());

        response.setItem(userDTO);
        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse updateUserInformation(UpdateUserInformationRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(request.getInfo().getUsername());
        if(!userEntityOptional.isPresent()){
            response.setResult(-1, "Người dùng không tồn tại");
            return response;
        }

        UserEntity userEntity = userEntityOptional.get();
        userEntity.setFullName(request.getFullName());
        userEntity.setNickname(request.getNickName());
        userEntity.setUsername(request.getNickName());
        userEntity.setEmail(request.getEmail());
        userEntity.setDescription(request.getDescription());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setAvatar(request.getAvatarUrl());

        userRepository.save(userEntity);

        response.setSuccess();
        return response;
    }
}
