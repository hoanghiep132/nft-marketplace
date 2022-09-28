package com.hiepnh.nftmarket.coresvc.domain.rabbit;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EventEntity {
    protected String type;
}