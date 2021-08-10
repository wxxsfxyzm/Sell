package com.carlyu.exception;

import com.carlyu.enums.ResultEnum;

public class SellException extends RuntimeException {
    private final Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
