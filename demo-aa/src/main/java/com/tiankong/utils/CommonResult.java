package com.tiankong.utils;

import lombok.Data;

@Data
public class CommonResult<T> {
    private Integer code;
    private T data;
    private String message;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 0;
        result.data = data;
        result.message = "ok";
        return result;
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.message = message;
        return result;
    }
}
