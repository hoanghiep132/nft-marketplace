package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DetailRequest {

    @NotNull(message = "id is not required")
    private Integer id;
}
