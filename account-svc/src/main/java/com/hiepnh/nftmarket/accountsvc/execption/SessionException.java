package com.hiepnh.nftmarket.accountsvc.execption;

public class SessionException extends Exception {
    public SessionException() {
    }

    public SessionException(String s) {
        super(s);
    }

    public SessionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SessionException(Throwable throwable) {
        super(throwable);
    }

    public SessionException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
