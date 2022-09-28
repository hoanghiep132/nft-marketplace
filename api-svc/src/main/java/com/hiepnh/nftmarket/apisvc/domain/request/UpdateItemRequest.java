package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class UpdateItemRequest extends CreateItemRequest{

    private String uuid;
}
