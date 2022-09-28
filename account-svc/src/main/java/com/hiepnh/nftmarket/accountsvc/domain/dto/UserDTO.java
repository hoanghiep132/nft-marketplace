package com.hiepnh.nftmarket.accountsvc.domain.dto;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import lombok.Data;

import javax.persistence.Column;
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
