package com.hiepnh.nftmarket.accountsvc.domain.request;

import lombok.Data;

@Data
public class UpdateUserInformationRequest extends BaseAuthRequest{

    private String fullName;

    private String nickName;

    private String description;

    private String email;

    private String phoneNumber;

    private String avatarUrl;

}
