package com.hiepnh.nftmarket.apisvc.entites.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NftItemContractModel {

    private String address;

    private Long tokenId;

    private String standard;

    private String network;

    public NftItemContractModel() {
        standard = "ERC-721";
        network = "BSC";
    }
}
