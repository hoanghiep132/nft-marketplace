package com.hiepnh.nftmarket.accountsvc.entities;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "wallet_type")
    private EnumCommon.WalletType walletType;

    @Column(name = "avatar")
    private String avatar;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nonce")
    private Long nonce;

    @Column(name = "role")
    private Integer role;

}
