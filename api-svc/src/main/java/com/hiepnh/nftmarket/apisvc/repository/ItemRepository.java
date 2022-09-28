package com.hiepnh.nftmarket.apisvc.repository;


import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

    @Query(value = "from ItemEntity i " +
            " where " +
            " (?1 is null or LOWER(i.name) like concat('%', ?1, '%')) " +
            " and " +
            " (?2 is null or i.collection.uuid = ?2 ) " +
            " and " +
            " ((?3 is null or ?3 = 0) or i.status = ?3) " +
            " and " +
            " (?4 is null or i.price >= ?4) " +
            " and " +
            " (?5 is null or i.price <= ?5) " +
            " order by i.createAt desc ")
    Page<ItemEntity> searchItemRecently(String text, String collectionUuid, Integer status, Double min, Double max, Pageable pageable);

    @Query(value = "from ItemEntity i " +
            " where " +
            " (?1 is null or LOWER(i.name) like concat('%', ?1, '%'))  " +
            " and " +
            " (?2 is null or i.collection.uuid = ?2 ) " +
            " and " +
            " ((?3 is null or ?3 = 0) or i.status = ?3) " +
            " and " +
            " (?4 is null or i.price >= ?4) " +
            " and " +
            " (?5 is null or i.price <= ?5) " +
            " order by i.price")
    Page<ItemEntity> searchItemAscPrice(String text, String collectionUuid, Integer status, Double min, Double max, Pageable pageable);

    @Query(value = "from ItemEntity i " +
            " where " +
            " (?1 is null or LOWER(i.name) like concat('%', ?1, '%'))  " +
            " and " +
            " (?2 is null or i.collection.uuid = ?2 ) " +
            " and " +
            " ((?3 is null or ?3 = 0) or i.status = ?3) " +
            " and " +
            " (?4 is null or i.price >= ?4) " +
            " and " +
            " (?5 is null or i.price <= ?5) " +
            " order by i.price desc ")
    Page<ItemEntity> searchItemDescPrice(String text, String collectionUuid, Integer status, Double min, Double max, Pageable pageable);

    @Query(value = "from ItemEntity i " +
            " where " +
            " (?1 is null or LOWER(i.name) like concat('%', ?1, '%'))  " +
            " and " +
            " (?2 is null or i.collection.uuid = ?2 ) " +
            " and " +
            " ((?3 is null or ?3 = 0) or i.status = ?3) " +
            " and " +
            " (?4 is null or i.price >= ?4) " +
            " and " +
            " (?5 is null or i.price <= ?5) " +
            " order by i.favoriteCount desc ")
    Page<ItemEntity> searchItemDescFavorite(String text, String collectionUuid, Integer status, Double min, Double max, Pageable pageable);

    @Query(value = "from ItemEntity  i where i.uuid = ?1 and i.status <> 0")
    Optional<ItemEntity> findByUuid(String uuid);

    @Query(value = "from ItemEntity  i where i.name = ?1 and i.status <> 0")
    Optional<ItemEntity> findByName(String uuid);

    @Query(value = "from ItemEntity  i where i.collection.uuid = ?1 and i.status <> 0")
    Page<ItemEntity> searchByCollectionUuid(String collectionUuid, Pageable pageable);

    @Query(value = "from ItemEntity i where i.collection.uuid = ?1 and i.status = ?2 and i.uuid <> ?3")
    List<ItemEntity> searchOthers(String collectionUuid, int status, String itemUuid);

    @Query(value = "from ItemEntity i where i.owner = ?1 and i.status <> 0")
    Page<ItemEntity> findByOwner(String walletAddress, Pageable pageable);
}
