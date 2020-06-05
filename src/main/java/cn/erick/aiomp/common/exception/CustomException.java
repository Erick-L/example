package cn.erick.aiomp.common.exception;

/**
 * 自定义异常
 * @Author Erick
 * @Date 2020/5/28 15:32
 * @Version 1.0
 */
public class CustomException extends RuntimeException{
    //异常状态码
    private Integer code;
    //异常信息
    private String msg;

    //构造
    public CustomException(String msg) {
        this.msg = msg;
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
}
