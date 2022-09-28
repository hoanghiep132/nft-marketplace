package com.hiepnh.nftmarket.coresvc.repository;

import com.hiepnh.nftmarket.coresvc.entites.AuctionEventDataEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuctionEventRepository extends CrudRepository<AuctionEventDataEntity, Long> {
}
