package com.run.conifg.modle;

public class RxBean<T> {

    private int type;
    private int code;
    private T data;
    private String msg;

    public RxBean(int type, int code) { this.type = type;this.code = code; }

    public RxBean(int type, int code, T data) {
        this.type = type;
        this.code = code;
        this.data = data;
    }

    public RxBean(int type, int code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
