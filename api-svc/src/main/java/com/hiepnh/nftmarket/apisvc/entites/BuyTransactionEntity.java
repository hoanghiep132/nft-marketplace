package com.hiepnh.nftmarket.apisvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "buy_transaction")
public class BuyTransactionEntity extends BaseEntity{

    @Column(name = "price")
    private Double price;

    @Column(name = "tnx_hash")
    private String txnHash;

    @Column(name = "block_hash")
    private String blockHash;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "item_market_id")
    private Long itemMarketId;
}
