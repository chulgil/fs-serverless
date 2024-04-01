package com.barodream.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PurchaseGoodsDto {
    private Long userId;
    private Long goodsId;
    private Integer quantity;
}
