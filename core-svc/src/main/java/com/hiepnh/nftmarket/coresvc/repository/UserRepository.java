package com.hiepnh.nftmarket.coresvc.repository;

import com.hiepnh.nftmarket.coresvc.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "from UserEntity u where u.id = ?1")
    Optional<UserEntity> findById(Integer id);

}
