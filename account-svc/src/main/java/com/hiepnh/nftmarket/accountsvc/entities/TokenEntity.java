package com.hiepnh.nftmarket.accountsvc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("TokenEntity")
public class TokenEntity {
    @Id
    private String token;

    private String username;

    private Long time;
}
