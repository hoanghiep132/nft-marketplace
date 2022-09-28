package com.hiepnh.nftmarket.gateway.repo;

import com.hiepnh.nftmarket.gateway.entities.SessionEntity;
import com.hiepnh.nftmarket.gateway.exception.SessionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {

    @Value("${app.session.timeout:900000}")
    private long timeout;

    private final SessionEntityRepo sessionEntityRepo;

    public SessionRepository(SessionEntityRepo sessionEntityRepo) {
        this.sessionEntityRepo = sessionEntityRepo;
    }

    public synchronized SessionEntity getSession(String token) throws SessionException {
        SessionEntity sessionEntity = sessionEntityRepo.findById(token).orElse(null);
        if (sessionEntity == null) {
            throw new SessionException("Session not existed -> login again");
        }
        return sessionEntity;
    }

}
