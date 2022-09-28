package com.hiepnh.nftmarket.coresvc.common;

import com.google.common.base.Strings;
import lombok.Data;

import java.util.List;

@Data
public class HeaderInfo {

    private String username;
    private String fullName;
    private Integer role;
    private List<String> permissions;

    public boolean validate() {
        return !Strings.isNullOrEmpty(username) && permissions != null;
    }

}
