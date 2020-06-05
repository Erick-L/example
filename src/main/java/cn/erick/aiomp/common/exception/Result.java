package cn.erick.aiomp.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回结果封装
 *
 * @Author Erick
 * @Date 2020/5/28 15:57
 * @Version 1.0
 */
public class Result {

    private static volatile Consequence consequence = new Consequence();
    private static int code;
    public static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    public static String SUCCESS = "0000";
    public static String FAIL = "0001";
    public static String PARAM_ERROR = "0002";
    public static String AUTH_ERROR = "0003";
    public static String SYSTEM_ERROR = "0004";
    public static String DEFAULT_ERROR = "10000";


    /**
     * 成功回调
     *
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject SUCCESS(String msg, Object data) {

        return getResult(SUCCESS, msg, data);
    }

    /**
     * 失败回调
     *
     * @param
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject FAIL(String msg, Object data) {

        return getResult(FAIL, msg, data);
    }

    /**
     * 参数错误
     *
     * @param
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject ParamError(String msg, Object data) {

        return getResult(PARAM_ERROR, msg, data);
    }


    /**
     * 认证错误
     *
     * @param
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject AuthError(String msg, Object data) {

        return getResult(AUTH_ERROR, msg, data);
    }

    /**
     * 系统错误
     *
     * @param
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject SystemError(String msg, Object data) {

        return getResult(SYSTEM_ERROR, msg, data);
    }

    /**
     * 默认异常
     *
     * @param
     * @param msg
     * @return
     */
    public static JSONObject DefaultError(String msg) {

        return getResult(DEFAULT_ERROR, msg, null);
    }

    public static JSONObject DefaultErrorByCodeAndMsg(String msg) {

        return getResult(DEFAULT_ERROR, msg, null);
    }

    public static JSONObject getResult(String code, String msg, Object data) {
        consequence.clear();
        consequence.setCode(code);
        consequence.setMsg(msg);
        consequence.setData(data);
        String info = JSON.toJSONStringWithDateFormat(consequence, dateFormat, SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(info);
    }

    public static JSONObject getResult(HttpServletResponse response, String code, String msg, Object data) {
        consequence.clear();
        consequence.setCode(code);
        consequence.setMsg(msg);
        consequence.setData(data);

        String info = JSON.toJSONStringWithDateFormat(consequence, dateFormat, SerializerFeature.DisableCircularReferenceDetect);

        return JSON.parseObject(info);
    }

    //响应到页面
    public static void responseResult(HttpServletResponse response, Object data) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 结果返回工具封装
 */
class Consequence {
    //错误状态码
    private String code;
    //错误信息
    private String msg;
    //返回数据
    private Object data;
    //异常详情
    private String detailMsg;

    public void clear() {
        this.code = null;
        this.msg = null;
        this.data = null;
        this.detailMsg = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }
}