package com.hiepnh.nftmarket.coresvc.processor;

import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.coresvc.entites.BidTransactionEntity;
import com.hiepnh.nftmarket.coresvc.entites.ItemEntity;
import com.hiepnh.nftmarket.coresvc.repository.AuctionRepository;
import com.hiepnh.nftmarket.coresvc.repository.BidTransactionRepository;
import com.hiepnh.nftmarket.coresvc.repository.ItemRepository;
import com.hiepnh.nftmarket.coresvc.smart_contracts.NftAuction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Optional;

public class EndAuctionThread extends AuctionBaseThread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NftAuction nftAuction;

    private AuctionRepository auctionRepository;

    private ItemRepository itemRepository;

    private BidTransactionRepository bidTransactionRepository;

    public EndAuctionThread(String itemUUid, NftAuction nftAuction, AuctionRepository auctionRepository, ItemRepository itemRepository, BidTransactionRepository bidTransactionRepository) {
        super(itemUUid);
        this.nftAuction = nftAuction;
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.bidTransactionRepository = bidTransactionRepository;
    }

    @Override
    protected void doAction() {
        logger.info("endAuction itemUuid: {}, time: {}", itemUUid, System.currentTimeMillis());

        Optional<AuctionEntity> auctionOptional = auctionRepository.findAvailableByItemUuid(itemUUid);
        if(!auctionOptional.isPresent()){
            return;
        }
        Long itemAuctionId = auctionOptional.get().getItemAuctionId();
        BigInteger itemAuctionIdBigInteger = new BigInteger(String.valueOf(itemAuctionId));

        try {
            TransactionReceipt transactionReceipt = this.nftAuction.endAuction(itemAuctionIdBigInteger).send();
            logger.info("transfer response txn_hash: {}", transactionReceipt);

            AuctionEntity auctionEntity = auctionOptional.get();
            auctionEntity.setUpdateBy("0x116e461E233f9Ed8579A2659FA92E3b50D8B4737");
            auctionEntity.setUpdateAt(System.currentTimeMillis());
            auctionEntity.setStatus(0);
            auctionEntity.setEndBlockHash(transactionReceipt.getBlockHash());
            auctionEntity.setEndTxnHash(transactionReceipt.getTransactionHash());
            auctionRepository.save(auctionEntity);

            Optional<BidTransactionEntity> bidTransactionEntityOptional = bidTransactionRepository.findHighestBid(auctionEntity.getId());
            String highestBidder;
            if(bidTransactionEntityOptional.isPresent()){
                highestBidder = bidTransactionEntityOptional.get().getCreateBy();
            }else {
                highestBidder = auctionEntity.getCreateBy();
            }

            Optional<ItemEntity> itemOptional = itemRepository.findByUuid(itemUUid);
            if(!itemOptional.isPresent()){
                return;
            }

            ItemEntity itemEntity = itemOptional.get();
            itemEntity.setStatus(Constant.ItemStatus.CREATED);
            itemEntity.setPrice(0D);
            itemEntity.setOwner(highestBidder);
            itemRepository.save(itemEntity);

        } catch (Exception e) {
            logger.info("End auction error: ", e);
            throw new RuntimeException(e);
        }
    }
}
