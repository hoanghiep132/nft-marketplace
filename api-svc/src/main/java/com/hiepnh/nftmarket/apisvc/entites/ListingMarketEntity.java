package com.hiepnh.nftmarket.apisvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "listing_market")
public class ListingMarketEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "price")
    private Double price;

    @Column(name = "tnx_hash")
    private String tnxHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "item_market_id")
    private Long itemMarketId;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Long updateAt;

    @Column(name = "status")
    private Integer status;
}
