package com.carlyu.enums;

/**
 * 约定枚举类都必须实现CodeEnum中的方法
 */
public interface CodeEnum<T> {
    T getCode();
}
