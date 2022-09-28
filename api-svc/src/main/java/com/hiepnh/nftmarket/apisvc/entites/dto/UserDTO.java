package com.hiepnh.nftmarket.apisvc.entites.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String username;

    private String fullName;

    private String nickname;

    private String email;

    private String description;

    private String walletAddress;

    private String avatar;

    private String phoneNumber;

    private List<CollectionDTO> collectionList;
}
