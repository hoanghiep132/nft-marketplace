package com.hiepnh.nftmarket.coresvc.repository;

import com.hiepnh.nftmarket.coresvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.coresvc.entites.BidTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BidTransactionRepository extends JpaRepository<BidTransactionEntity, Integer> {

    @Query(nativeQuery = true, value = "select * from bid_transaction b where b.auction_id = ?1 order by b.id desc limit 1 ")
    Optional<BidTransactionEntity> findHighestBid(Integer auctionId);


//    Optional<BidTransactionEntity> findTopByAuctionOrOrderByCreateAtDesc(AuctionEntity auction);
}
