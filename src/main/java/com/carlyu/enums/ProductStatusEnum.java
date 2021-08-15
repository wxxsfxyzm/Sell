package com.carlyu.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum<Integer> {
    UP(0, "上架"), DOWN(1, "下架");
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}