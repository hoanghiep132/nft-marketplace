package com.hiepnh.nftmarket.apisvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "collection")
public class CollectionEntity extends BaseEntity{

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "categories")
    private String categories;

    @Column(name = "image")
    private String image;

    @Column(name = "banner_img")
    private String bannerImg;

    @Column(name = "status")
    private Integer status;

    @Column(name = "txn_hash")
    private String txnHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "total")
    private Integer total;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Long updateAt;
}
