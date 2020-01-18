package com.hovvyoung.mall.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(0),

    CUSTOM(1)

    ;

    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
