package com.hiepnh.nftmarket.coresvc.common;

public class MyExceptionDefs {
    public static final int SSLHandshakeException = 1008;
    public static final int SocketTimeoutException = 1009;

    public static boolean checkExceptionConnectSSL(int code) {
        return code == SSLHandshakeException;
    }

}
