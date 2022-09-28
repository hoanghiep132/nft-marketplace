package com.hiepnh.nftmarket.coresvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "bid_transaction")
public class BidTransactionEntity extends BaseEntity{

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    @Column(name = "price")
    private Double price;

    @Column(name = "txn_hash")
    private String txnHash;

    @Column(name = "block_hash")
    private String blockHash;
}
