package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.ListingMarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ListingMarketRepository extends JpaRepository<ListingMarketEntity, Integer> {

    @Query(value = "from ListingMarketEntity  lm where lm.item.uuid = ?1 and lm.status = 1")
    Optional<ListingMarketEntity> findByItemUuid(String itemUuid);
}
