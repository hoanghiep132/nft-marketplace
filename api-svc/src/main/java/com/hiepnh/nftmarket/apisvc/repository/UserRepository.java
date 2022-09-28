package com.hiepnh.nftmarket.apisvc.repository;

import com.hiepnh.nftmarket.apisvc.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "from UserEntity  u where u.id = ?1")
    UserEntity findUserEntityById(Integer id);

    @Query(value = "from UserEntity u where u.walletAddress = ?1")
    Optional<UserEntity> findByWalletAddress(String walletAddress);
}
