package com.carlyu.util;

import com.carlyu.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        // 遍历枚举
        for (T eachEnum : enumClass.getEnumConstants()) {
            // 判断Code是否相等
            if (code.equals(eachEnum.getCode())) {
                return eachEnum;
            }
        }
        return null;
    }

}
