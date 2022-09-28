package com.hiepnh.nftmarket.accountsvc.entities;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private Long createAt;

}
