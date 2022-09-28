package com.hiepnh.nftmarket.coresvc.entites;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "auction")
public class AuctionEntity extends BaseEntity{

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "start_price")
    private Double startPrice;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "update_at")
    private Long updateAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "status")
    private Integer status;

    @Column(name = "txn_hash")
    private String txnHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "item_auction_id")
    private Long itemAuctionId;

    @Column(name = "end_txn_hash")
    private String endTxnHash;

    @Column(name = "end_block_hash")
    private String endBlockHash;
}
