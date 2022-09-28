package com.hiepnh.nftmarket.accountsvc.common;

public class Common {
    public static final String USER_ID_IN_HEADER = "X-USER-ID";
    public static final String USER_DISPLAY_NAME_IN_HEADER = "X-DISPLAY-NAME";
    public static final String USER_ID_ROLE_HEADER = "X-USER-ROLE";
    public static final String PERMISSION_HEADER = "X-PERMISSION";
    public static final String SECRET = "KKK-ZZZ-###-^^^-123";

    public static final int ACC_STATUS_ACTIVE = 1;
    public static final int ACC_STATUS_PENDING = 2;
    public static final int ACC_STATUS_BANNED = 0;

    public static final int TOKEN_EXPIRED_TIME = 3_600_000;

    public static final int NONCE_EXPIRED_TIME = 3_600_000;

    public static final String DEFAULT_USERNAME = "Unnamed";
}