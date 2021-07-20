package com.sensen.bms.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回类型
 */
public class Msg {
    private Integer code;
    private String msg;
    //用户返回给浏览器的数据
    private Map<String,Object> data = new HashMap<String,Object>();

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public static Msg error(){
        Msg result = new Msg();
        result.setCode(400);
        result.setMsg("error");
        return result;
    }

    public Msg add(String key,Object value){    //可链式操作
        this.getData().put(key,value);
        return this;
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
