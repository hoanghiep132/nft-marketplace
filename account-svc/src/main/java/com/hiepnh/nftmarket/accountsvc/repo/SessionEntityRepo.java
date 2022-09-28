package com.hiepnh.nftmarket.accountsvc.repo;

import com.hiepnh.nftmarket.accountsvc.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SessionEntityRepo extends CrudRepository<SessionEntity, String> {
}
