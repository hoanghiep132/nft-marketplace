package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.BuyTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyTransactionRepository extends JpaRepository<BuyTransactionEntity, Integer> {

    @Query(value = "from BuyTransactionEntity  b where b.item.uuid = ?1")
    List<BuyTransactionEntity> getAllByItem(String itemUuid);
}
