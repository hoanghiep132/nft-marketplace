package com.hiepnh.nftmarket.coresvc.repository;


import com.hiepnh.nftmarket.coresvc.entites.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query(value = "from ItemEntity  i where i.uuid = ?1")
    Optional<ItemEntity> findByUuid(String uuid);
}
