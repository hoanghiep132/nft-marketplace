package com.hiepnh.nftmarket.apisvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "item")
public class ItemEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private CollectionEntity collection;

    @Column(name = "favorite_count")
    private Integer favoriteCount;

    @Column(name = "txn_hash")
    private String txnHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "owner")
    private String owner;
}
