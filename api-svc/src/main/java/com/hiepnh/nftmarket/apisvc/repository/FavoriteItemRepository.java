package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.FavoriteItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItemEntity, Integer> {

    @Query(value = "from FavoriteItemEntity  f where f.item.uuid = ?1 and f.createBy = ?2")
    Optional<FavoriteItemEntity> findFavorite(String itemUuid, String userAddress);

    @Query(value = "from FavoriteItemEntity  f where f.item.collection.uuid = ?1 and f.createBy = ?2")
    List<FavoriteItemEntity> findFavoriteByCollectionUuid(String collectionUuid, String userAddress);

    @Query(value = "from FavoriteItemEntity f where f.createBy = ?1")
    Page<FavoriteItemEntity> findByUser(String userAddress, Pageable pageable);
}
