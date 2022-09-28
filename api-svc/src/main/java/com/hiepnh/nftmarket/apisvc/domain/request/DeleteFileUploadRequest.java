package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeleteFileUploadRequest extends BaseAuthRequest {

    private List<String> fileUrlList;
}