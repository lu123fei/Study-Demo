package com.jd.demo.common.lang;

import lombok.Data;

/**
 * 全局结果返回类
 * @author lufei
 * @date 2023/5/17 17:40
 */
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
    //定义返回操作成功的结果形式（状态码：200，操作成功的相关信息，返回的数据）
    public static Result success(Object data){
        return success(200,"操作成功",data);
    }
    public static Result success(String msg){
        return success(200,msg,null);
    }
    public static Result success(String msg,Object data){
        return success(200,msg,data);
    }
    public static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    //定义返回操作失败的结果形式（状态码：500，操作失败的相关信息，返回的数据）
    public static Result fail(String msg) {
        return fail(500, msg, null);
    }
    public static Result fail(String msg, Object data) {
        return fail(500, msg, data);
    }
    private static Result fail(Object data){
        return success(500,"操作失败",data);
    }
    public static Result fail(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
