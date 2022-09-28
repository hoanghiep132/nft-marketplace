package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class DeleteDataRequest extends BaseAuthRequest{

    private String uuid;
}
