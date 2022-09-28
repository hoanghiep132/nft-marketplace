package com.hiepnh.nftmarket.coresvc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagingInfo {

    public static final int START_PAGE = 0;
    public static final int SIZE_PAGE = 100;
    private Integer start;
    private Integer limit;

    // start from client start 1
    public static PagingInfo parse(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = START_PAGE;
        }
        if (page > 0) {
            page--;
        }
        if (size == null || size < 0) {
            size = SIZE_PAGE;
        }
        return PagingInfo.builder()
                .start(page * size)
                .limit(size)
                .build();
    }

}
