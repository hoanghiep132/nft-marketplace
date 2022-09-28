package com.hiepnh.nftmarket.accountsvc.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetSingleItemResponse<T> extends BaseResponse {
    protected T item;
}
