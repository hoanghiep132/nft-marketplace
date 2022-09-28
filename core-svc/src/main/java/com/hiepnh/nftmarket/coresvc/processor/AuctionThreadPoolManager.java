package com.hiepnh.nftmarket.coresvc.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AuctionThreadPoolManager {

    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private static AuctionThreadPoolManager ourInstance = new AuctionThreadPoolManager();

    public static AuctionThreadPoolManager getInstance() {
        return ourInstance;
    }

    private AuctionThreadPoolManager() {
    }


    public void addTask(AuctionBaseThread thread){
        threadPoolExecutor.submit(thread);
    }

}
