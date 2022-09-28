package com.hiepnh.nftmarket.coresvc.entites;

import com.hiepnh.nftmarket.coresvc.domain.dto.AuctionData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("AuctionEventEntity")
public class AuctionEventDataEntity {

    @Id
    private Long time;

    private List<AuctionData> data;
}
