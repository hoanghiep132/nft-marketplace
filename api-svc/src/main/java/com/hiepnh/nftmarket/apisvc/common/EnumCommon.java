package com.hiepnh.nftmarket.apisvc.common;

public class EnumCommon {
    public enum OrderSort {
        LOW2HIGH,
        HIGH2LOW,
        RECENTLY_LISTED,
        RECENTLY_CREATED,
        MOST_VIEW,
        MOST_FAVORITE
    }

    public enum ItemStatus {
        BUY_NOW,
        ON_AUCTION,
        HAS_OFFER,
        DELETED,
    }

    public enum Category{
        ART,
        PHOTOGRAPHY,
        MUSIC,
        GAMING,
        SPORT
    }
}
