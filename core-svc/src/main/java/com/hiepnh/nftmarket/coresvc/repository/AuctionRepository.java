package com.hiepnh.nftmarket.coresvc.repository;


import com.hiepnh.nftmarket.coresvc.entites.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {

    @Query(value = "from AuctionEntity  a where  a.item.uuid = ?1 and a.status = 1")
    Optional<AuctionEntity> findAvailableByItemUuid(String itemUuid);

}
