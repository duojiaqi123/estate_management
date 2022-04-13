package com.djq.estate_management.Common;

import java.io.Serializable;

/**
 * @Auth: DUOJIAQI
 * @Desc: 密码的Result
 */
public class Result<P> implements Serializable {

    private static final long serialVersionUID = -2435089504958177374L;

    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回消息
    private Object data;
    /**
     * 功能描述: 无参构造
     * @return :
     */
    public Result() {
    }
    /**
     * 功能描述: 没有数据集结果的有参构造
     * @param flag  请求是否成功
     * @param code  请求返回状态码
     * @param message   请求返回消息提示
     * @return : 返回结果对象
     */
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
    /**
     * 功能描述: 有数据集结果的有参构造
     * @param flag  请求是否成功
     * @param code  请求返回状态码
     * @param message   请求返回消息提示
     * @param data  返回页面数据
     * @return : 返回结果对象
     */
    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
    public static <P> Result<P> error(Integer code, String message) {
        Result<P> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }
    public static <P> Result<P> success(P data) {
        Result<P> result = new Result<>();
        result.code = 0;
        result.message = "成功";
        result.setData(data);
        return result;
    }
}
