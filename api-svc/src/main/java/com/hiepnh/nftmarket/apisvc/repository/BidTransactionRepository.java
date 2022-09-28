package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.BidTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BidTransactionRepository extends JpaRepository<BidTransactionEntity, Integer> {

    @Query(value = "from BidTransactionEntity b where b.auction.uuid = ?1 order by b.createAt desc ")
    Page<BidTransactionEntity> searchByAuction(String auctionUuid, Pageable pageable);

    @Query(value = "from BidTransactionEntity b where b.auction.item.uuid = ?1 order by b.createAt desc ")
    List<BidTransactionEntity> findAllByItemUuid(String itemUuid);
}
