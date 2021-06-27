package com.tc.common;

import com.fasterxml.jackson.annotation.JsonInclude;

public class R<T> {

    private Integer code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public R() {
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> R<T> ok() {
        return new R<>(0, "请求成功");
    }

    public static <T> R<T> error() {
        return new R<>();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
