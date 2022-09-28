package com.hiepnh.nftmarket.apisvc.common;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.apisvc.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseHeaderUtil {
    public static HeaderInfo build(Map<String, String> headers) {
        final HeaderInfo info = new HeaderInfo();
        headers.forEach((key, value) -> {
            if (!Strings.isNullOrEmpty(key)) {
                if (key.equalsIgnoreCase(Common.USER_ID_IN_HEADER)) {
                    info.setUsername(value);
                }
                if (key.equalsIgnoreCase(Common.USER_ID_ROLE_HEADER)) {
                    info.setRole(AppUtils.parseInt(value));
                }
                if (key.equalsIgnoreCase(Common.PERMISSION_HEADER)) {
                    // fake
                    List<String> pers = new ArrayList<>();
                    pers.add(value);
                    info.setPermissions(pers);
                }
                if (key.equalsIgnoreCase(Common.USER_DISPLAY_NAME_IN_HEADER)) {
                    info.setFullName(value);
                }
            }
        });
        return info;
    }

}
