package com.hiepnh.nftmarket.apisvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import lombok.Data;

@Data
public class UpdateCollectionRequest extends CreateCollectionRequest{

    private String uuid;

    public BaseResponse validate(){
        super.validate();
        if(Strings.isNullOrEmpty(uuid)){
            return new BaseResponse(-1, "Vui lòng chọn bộ sưu tập");
        }

        return null;
    }
}
