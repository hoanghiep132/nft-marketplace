package com.hiepnh.nftmarket.accountsvc.repo;

import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "from UserEntity u where u.walletAddress = ?1 and u.walletType = ?2")
    Optional<UserEntity> findByWalletAddress(String walletAddress, EnumCommon.WalletType walletType);

    @Query(value = "from UserEntity u where u.walletAddress = ?1")
    Optional<UserEntity> findByWalletAddress(String walletAddress);

    @Query(value = "from UserEntity u where u.username = ?1")
    Optional<UserEntity> findUsername(String username);

    @Query(value = "from UserEntity u where u.id = ?1")
    Optional<UserEntity> findById(Integer id);

}
