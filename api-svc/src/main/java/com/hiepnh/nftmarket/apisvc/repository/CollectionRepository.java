package com.hiepnh.nftmarket.apisvc.repository;


import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Integer> {

    @Query(value = "from CollectionEntity c " +
            " where " +
            " (?1 is null or c.name like concat('%', ?1, '%')) " +
            " and " +
            " (?2 is null or c.categories like concat('%', ?2, '%')) " +
            " and " +
            " c.status = 1")
    Page<CollectionEntity> searchCollection(String text, String category, Pageable pageable);

    @Query(value = "from CollectionEntity c where  c.uuid = ?1 and c.status = 1")
    Optional<CollectionEntity> findByUuid(String uuid);

    @Query(value = "from CollectionEntity c where  c.name = ?1 and c.status = 1")
    Optional<CollectionEntity> findByName(String name);

    @Query(value = "from CollectionEntity c where  c.createBy = ?1 and c.status = 1")
    Page<CollectionEntity> findByUsername(String username, Pageable pageable);

    @Query(value = "from CollectionEntity c where  c.createBy = ?1 and c.status = 1")
    List<CollectionEntity> getAllByUsername(String username);

}
