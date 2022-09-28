package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {

    @Query(value = "from AuctionEntity  a where  a.uuid = ?1")
    Optional<AuctionEntity> findByUuid(String uuid);

    @Query(value = "from AuctionEntity  a where  a.uuid = ?1 and a.status = 1")
    Optional<AuctionEntity> findAvailableByUuid(String uuid);

    @Query(value = "from AuctionEntity a where  a.item.uuid = ?1 and a.status = 1")
    Optional<AuctionEntity> findByItemUuid(String itemUuid);
}
