package com.li.reggie.utils;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private T data;
    private String msg;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.code = 1;
        result.data = data;
        return result;
    }

    public static <T> Result<T> err(String msg) {
        Result<T> result = new Result<T>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
