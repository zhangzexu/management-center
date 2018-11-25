package com.ym52n.domain.result;

public class ResponsePageData{
    private Object data;
    private Long count;
    private String code;
    private String msg;
    public ResponsePageData(String rspCode, String rspMsg, Object data,Long count) {
        this.code = rspCode;
        this.msg = rspMsg;
        this.data = data;
        this.count = count;
    }

    public ResponsePageData(ExceptionMsg msg, Object data,Long count) {
        this.code = msg.getCode();
        this.msg = msg.getMsg();
        this.data = data;
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
