package com.hiepnh.nftmarket.coresvc.processor;

import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.domain.dto.AuctionData;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEventDataEntity;
import com.hiepnh.nftmarket.coresvc.repository.AuctionEventRepository;
import com.hiepnh.nftmarket.coresvc.repository.AuctionRepository;
import com.hiepnh.nftmarket.coresvc.repository.BidTransactionRepository;
import com.hiepnh.nftmarket.coresvc.repository.ItemRepository;
import com.hiepnh.nftmarket.coresvc.smart_contracts.DynamicGasProvider;
import com.hiepnh.nftmarket.coresvc.smart_contracts.AuctionDynamicGas;
import com.hiepnh.nftmarket.coresvc.smart_contracts.NftAuction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;

import java.util.List;
import java.util.Optional;

@Component
public class AuctionScheduledTask {

    private final AuctionEventRepository auctionEventRepository;

    private final ItemRepository itemRepository;

    private final AuctionRepository auctionRepository;

    private final BidTransactionRepository bidTransactionRepository;

    @Value("${application.smart_contract_config.auction_contract_address}")
    private String nftAuctionAddress;

    @Value("${application.smart_contract_config.network_id}")
    private Long networkId;

    @Value("${application.smart_contract_config.operator}")
    private String privateKey;

    @Value("${application.smart_contract_config.rpc_url}")
    private String rpcUrl;

    public AuctionScheduledTask(AuctionEventRepository auctionEventRepository, ItemRepository itemRepository, AuctionRepository auctionRepository, BidTransactionRepository bidTransactionRepository) {
        this.auctionEventRepository = auctionEventRepository;
        this.itemRepository = itemRepository;
        this.auctionRepository = auctionRepository;
        this.bidTransactionRepository = bidTransactionRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void startAuctionSchedule() {

        long time = System.currentTimeMillis() / 1000;
        Optional<AuctionEventDataEntity> auctionEventOptional = auctionEventRepository.findById(time);
        if(!auctionEventOptional.isPresent()){
            return;
        }

        List<AuctionData> auctionData = auctionEventOptional.get().getData();
        for (AuctionData data: auctionData){
            AuctionBaseThread thread;
            if(data.getType() == Constant.AuctionMode.START){
                thread = new StartAuctionThread(data.getItemUuid(), itemRepository);
            }else if(data.getType() == Constant.AuctionMode.END){
                NftAuction nftAuction = init();
                Optional<AuctionEntity> auctionOptional = auctionRepository.findAvailableByItemUuid(data.getItemUuid());
                if(!auctionOptional.isPresent()){
                    return;
                }
                thread = new EndAuctionThread(data.getItemUuid(), nftAuction, auctionRepository,itemRepository, bidTransactionRepository);
            }else {
                continue;
            }
            AuctionThreadPoolManager.getInstance().addTask(thread);
        }

    }

    private NftAuction init(){
        Web3j web3 = Web3j.build(new HttpService(this.rpcUrl));
        RawTransactionManager transactionManager = new RawTransactionManager(
                web3,
                Credentials.create(privateKey),
                networkId
        );
        AuctionDynamicGas nftAuction = AuctionDynamicGas.load(
                this.nftAuctionAddress,
                web3,
                transactionManager,
                new DynamicGasProvider(web3)
        );

        return nftAuction;
    }

}
