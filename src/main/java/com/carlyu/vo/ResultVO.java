package com.carlyu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * http 请求返回的最外层对象
 */
@Data
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {
    /**
     * 序列化 ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 错误码.
     */
    private Integer code;
    /**
     * 提示信息.
     */
    private String msg = "";
    /**
     * 具体内容.
     */
    private T data;
}
