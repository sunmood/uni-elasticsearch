package com.uni.unielasticsearch.utils;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message = "";
    private T data;

    private Result(int code) {
        this.code = code;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result(200);
    }

    public static <T> Result<T> ok(T data) {
        return new Result(200, data);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result(200, message, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result(500, message);
    }
}