package com.hiepnh.nftmarket.coresvc;

import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.coresvc.entites.BidTransactionEntity;
import com.hiepnh.nftmarket.coresvc.entites.ItemEntity;
import com.hiepnh.nftmarket.coresvc.repository.AuctionRepository;
import com.hiepnh.nftmarket.coresvc.repository.BidTransactionRepository;
import com.hiepnh.nftmarket.coresvc.repository.ItemRepository;
import com.hiepnh.nftmarket.coresvc.smart_contracts.DynamicGasProvider;
import com.hiepnh.nftmarket.coresvc.smart_contracts.AuctionDynamicGas;
import com.hiepnh.nftmarket.coresvc.smart_contracts.NftAuction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;

import java.math.BigInteger;
import java.util.Optional;

@Component
public class Test implements CommandLineRunner {

    @Value("${application.smart_contract_config.auction_contract_address}")
    private String nftAuctionAddress;

    @Value("${application.smart_contract_config.network_id}")
    private Long networkId;

    @Value("${application.smart_contract_config.operator}")
    private String privateKey;

    @Value("${application.smart_contract_config.rpc_url}")
    private String rpcUrl;

    private final AuctionRepository auctionRepository;

    private final ItemRepository itemRepository;

    private final BidTransactionRepository bidTransactionRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Test(AuctionRepository auctionRepository, ItemRepository itemRepository, BidTransactionRepository bidTransactionRepository) {
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.bidTransactionRepository = bidTransactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void test(){
        String itemUUid = "db714def-edb7-42bf-b6c9-e4b1a347344b";
        Web3j web3 = Web3j.build(new HttpService(this.rpcUrl));
        RawTransactionManager transactionManager = new RawTransactionManager(
                web3,
                Credentials.create(privateKey),
                networkId
        );
        NftAuction nftAuction = AuctionDynamicGas.load(
                this.nftAuctionAddress,
                web3,
                transactionManager,
                new DynamicGasProvider(web3)
        );
//        AuctionBaseThread thread = new EndAuctionThread("23ae3fc0-d063-49bc-a801-44ae704d405d", nftAuction, auctionRepository, itemRepository, bidTransactionRepository);
//        AuctionThreadPoolManager.getInstance().addTask(thread);

        Optional<AuctionEntity> auctionOptional = auctionRepository.findAvailableByItemUuid(itemUUid);
        if(!auctionOptional.isPresent()){
            return;
        }
        Long itemAuctionId = auctionOptional.get().getItemAuctionId();
        BigInteger itemAuctionIdBigInteger = new BigInteger(String.valueOf(itemAuctionId));

        try {
            TransactionReceipt transactionReceipt = nftAuction.endAuction(itemAuctionIdBigInteger).send();
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
            itemEntity.setOwner(highestBidder);
            itemRepository.save(itemEntity);

        } catch (Exception e) {
            logger.info("End auction error: ", e);
            throw new RuntimeException(e);
        }
    }

}
