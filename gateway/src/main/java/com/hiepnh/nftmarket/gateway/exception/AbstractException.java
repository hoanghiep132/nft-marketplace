package com.hiepnh.nftmarket.gateway.exception;

import org.springframework.http.HttpStatus;

public class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    private final int code;

    private final String message;

    private final String detailMessage;

    public AbstractException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.detailMessage = errorCode.getDetailMessage();
    }

    public AbstractException(HttpStatus status) {
        this.status = status;
        code = 0;
        message = null;
        detailMessage = null;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return new ErrorCode(code, message, detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

}

