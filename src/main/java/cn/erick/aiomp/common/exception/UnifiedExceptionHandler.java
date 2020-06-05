package cn.erick.aiomp.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.BindException;

/**
 * 统一异常处理
 *
 * @Author Erick
 * @Date 2020/5/28 9:46
 * @Version 1.0
 */
public class UnifiedExceptionHandler {


    /**
     * 统一异常处理 -- 基础异常处理
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONObject exceptionHandler(Exception e) {

        e.printStackTrace();

        if (e instanceof BindException) {
            int index = e.getMessage().lastIndexOf("[");
            Result.DefaultErrorByCodeAndMsg(index != -1 ? e.getMessage().substring(index + 1, e.getMessage().length() - 1).trim() : e.getMessage());

        }
        return Result.DefaultError(e.toString());
    }

    /**
     * 统一异常处理 -- 自定义异常处理
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public JSONObject exceptionHandler(CustomException e) {
        e.printStackTrace();
        if (e.getCode() == null) {
            e.setCode(4);
        }
        return Result.DefaultErrorByCodeAndMsg(e.getMsg());
    }
}