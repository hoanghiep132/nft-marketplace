package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.UserEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.UserDTO;
import com.hiepnh.nftmarket.apisvc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CollectionService collectionService;

    public UserServiceImpl(UserRepository userRepository, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.collectionService = collectionService;
    }

    @Override
    public GetSingleItemResponse<UserEntity> findUserById(Integer id) {
        log.info("Begin find user with id: {}", id);
        GetSingleItemResponse<UserEntity> response = new GetSingleItemResponse<>();
        response.setSuccess();
        response.setItem(userRepository.findUserEntityById(id));
        return response;
    }

    @Override
    public GetSingleItemResponse<UserDTO> findByInfoWalletAddress(String walletAddress) {
        GetSingleItemResponse<UserDTO> response = new GetSingleItemResponse<>();

        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(walletAddress);
        if(!userEntityOptional.isPresent()){
            response.setResult(-1, "Not found");
            return response;
        }

        UserEntity userEntity = userEntityOptional.get();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUserName());
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setWalletAddress(userEntity.getWalletAddress());
        userDTO.setAvatar(userEntity.getAvatar());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setDescription(userEntity.getDescription());

        GetArrayResponse<CollectionDTO> collectionListResp = collectionService.searchCollectionByWalletAddress(1, 100, walletAddress);
        if(collectionListResp.getRows() != null){
            userDTO.setCollectionList(collectionListResp.getRows());
        }

        response.setItem(userDTO);
        response.setSuccess();
        return response;
    }
}
