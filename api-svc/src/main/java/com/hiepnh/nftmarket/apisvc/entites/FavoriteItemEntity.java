package com.hiepnh.nftmarket.apisvc.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "favorite_item")
public class FavoriteItemEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;


}
