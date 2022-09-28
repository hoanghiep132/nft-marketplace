package com.hiepnh.nftmarket.accountsvc.repo;

import com.hiepnh.nftmarket.accountsvc.entities.UserSessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionEntityRepo extends CrudRepository<UserSessionEntity, String> {
}
