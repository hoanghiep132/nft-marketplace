package com.hiepnh.nftmarket.accountsvc.execption;

import org.springframework.http.HttpStatus;

public class ServerException extends AbstractException {
    public ServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServerException(ErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public ServerException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}