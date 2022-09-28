package com.hiepnh.nftmarket.coresvc.processor;

import com.hiepnh.nftmarket.coresvc.entites.ItemEntity;
import com.hiepnh.nftmarket.coresvc.repository.ItemRepository;

import java.util.Optional;

public class StartAuctionThread extends AuctionBaseThread{

    private final ItemRepository itemRepository;

    public StartAuctionThread(String itemUUid, ItemRepository itemRepository) {
        super(itemUUid);
        this.itemRepository = itemRepository;
    }

    @Override
    protected void doAction() {
        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(itemUUid);

        logger.info("Data: {}", itemOptional);
    }
}
