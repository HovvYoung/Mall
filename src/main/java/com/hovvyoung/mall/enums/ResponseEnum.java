package com.hovvyoung.mall.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1, "server error"),

    SUCCESS(0, "success"),

    PASSWORD_ERROR(1, "password error"),

    USERNAME_EXIST(2, "username exist"),

    PARAM_ERROR(3, "parameter error"),

    EMAIL_EXIST(4, "email exist"),

    NEED_LOGIN(10, "please login in first"),

    USERNAME_OR_PASSWORD_ERROR(11, "username or password error"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "product no long sold or be deleted"),

    PRODUCT_NOT_EXIST(13, "product not exist"),

    PROODUCT_STOCK_ERROR(14, "库存不正确"),

    CART_PRODUCT_NOT_EXIST(15, "购物车里无此商品"),

    DELETE_SHIPPING_FAIL(16, "删除收货地址失败"),

    SHIPPING_NOT_EXIST(17, "收货地址不存在"),

    CART_SELECTED_IS_EMPTY(18, "请选择商品后下单"),

    ORDER_NOT_EXIST(19, "订单不存在"),

    ORDER_STATUS_ERROR(20, "订单状态有误"),

    ;

    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
