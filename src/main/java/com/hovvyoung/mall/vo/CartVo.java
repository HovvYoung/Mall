package com.hovvyoung.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectAll;

    private Integer cartTotalQuantity;

    private BigDecimal cartTotalPrice;
}
