package com.hiepnh.nftmarket.apisvc.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Column(name = "username")
    private String userName;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "wallet_type")
    private String walletType;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nonce")
    private Long nonce;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private Integer role;

}
