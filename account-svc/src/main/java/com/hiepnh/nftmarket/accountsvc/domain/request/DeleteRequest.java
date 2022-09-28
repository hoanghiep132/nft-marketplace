package com.hiepnh.nftmarket.accountsvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import lombok.Data;

@Data
public class DeleteRequest extends BaseAuthRequest {

    private String id;

    public BaseResponse validate() {
        if (Strings.isNullOrEmpty(id)) {
            return new BaseResponse(-1, "Yêu cầu không hợp lệ");
        }
        return null;
    }
}
