package com.hiepnh.nftmarket.apisvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateCollectionRequest extends BaseAuthRequest{

    private String name;

    private String symbol;

    private String description;

    private String imageUrl;

    private String bannerUrl;

    private List<String> categories;

    private String txnHash;

    private String blockHash;

    private String contractAddress;

    public BaseResponse validate(){

        if(Strings.isNullOrEmpty(name)){
            return new BaseResponse(-1, "Vui lòng điền tên bộ sưu tập");
        }

        return null;
    }
}
