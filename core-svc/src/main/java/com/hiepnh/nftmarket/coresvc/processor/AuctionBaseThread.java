package com.hiepnh.nftmarket.coresvc.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AuctionBaseThread implements Runnable{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String itemUUid;

    public AuctionBaseThread(String itemUUid) {
        this.itemUUid = itemUUid;
    }

    @Override
    public void run() {
        doAction();
    }

    protected abstract void doAction();
}
