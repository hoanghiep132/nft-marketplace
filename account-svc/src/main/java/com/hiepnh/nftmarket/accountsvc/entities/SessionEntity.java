package com.hiepnh.nftmarket.accountsvc.entities;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("SessionEntity")
public class SessionEntity implements Serializable {
    @Id
    private String token;

    private String walletAddress;

    private EnumCommon.WalletType walletType;
}
