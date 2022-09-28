package com.hiepnh.nftmarket.apisvc.domain.rabbitmq;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EventEntity {
    protected String type;
}