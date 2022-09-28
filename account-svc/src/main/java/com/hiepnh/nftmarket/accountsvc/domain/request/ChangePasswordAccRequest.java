package com.hiepnh.nftmarket.accountsvc.domain.request;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.accountsvc.domain.helper.AppUtils;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ChangePasswordAccRequest extends BaseAuthRequest {
    private String username;
    private String newPassword;
    private String oldPassword;

    public BaseResponse validate() {
        BaseResponse response = new BaseResponse();
        if (Strings.isNullOrEmpty(username)) {
            response.setResult(-1, "Vui lòng nhập tên đăng nhập");
            return response;
        }
        if (!AppUtils.validateUsername(username)) {
            response.setResult(-1, "Tên đăng nhập không đúng");
            return response;
        }
        if (Strings.isNullOrEmpty(newPassword)) {
            response.setResult(-1, "Vui lòng nhập mật khẩu mới");
            return response;
        }
        if (!AppUtils.checkNormalIsdn(newPassword)) {
            response.setResult(-1, "Mật khẩu không đúng định dạng");
            return response;
        }
        if (Strings.isNullOrEmpty(oldPassword)) {
            response.setResult(-1, "Vui lòng nhập mật khẩu cũ");
            return response;
        }
        if (!AppUtils.checkNormalIsdn(oldPassword)) {
            response.setResult(-1, "Mật khẩu không đúng định dạng");
            return response;
        }
        return null;
    }
}
