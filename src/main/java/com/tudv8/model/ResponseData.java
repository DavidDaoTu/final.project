package com.tudv8.model;

public class ResponseData {
    private int errCode;
    private Object data;
    private String message;

    public ResponseData(int errCode, Object data, String message) {
        this.errCode = errCode;
        this.data = data;
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
