package com.hiepnh.nftmarket.apisvc.common;

public class Constant {

    public static class ItemStatus{
        public static final int DELETED = 0;
        public static final int CREATED = 1;
        public static final int LISTING = 2;
        public static final int AUCTION = 3;
    }

    public static class StatusCode {
        private StatusCode() {
        }

        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int ACCEPTED = 202;
        public static final int REDIRECT = 301;
        public static final int FOUND = 302;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int CONFLICT = 409;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

}
