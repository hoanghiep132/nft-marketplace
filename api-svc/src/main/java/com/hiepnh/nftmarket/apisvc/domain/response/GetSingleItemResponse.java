package com.hiepnh.nftmarket.apisvc.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GetSingleItemResponse<T> extends BaseResponse {
    protected T item;
}
