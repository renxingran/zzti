package com.trendhu.common.conf;

import java.util.HashMap;
import java.util.Map;

public class StatusCode {

    public static final Map<String, String> MSG_MAP = new HashMap<String, String>();
    static {
        MSG_MAP.put("A001", "接口调用成功");
        MSG_MAP.put("A002", "服务器内部错");
    }

    // 接口调用成功
    public static final String CODE_SUCCESS = "A001";
    // 接口没有数据
    public static final String CODE_NO_DATA = "A002";
    // 接口的参数格式错误
    public static final String CODE_PARAM_FORMAT_ERROR = "A003";
    // 调用接口没有权限
    public static final String CODE_NO_AUTH = "A004";
    // 调用接口发生内部错误
    public static final String CODE_INTERNAL_ERROR = "A005";
    // 调用接口的参数有问题
    public static final String CODE_PARAM_CONTENT_ERROR = "A006";
    // 接口的授权已经超时
    public static final String CODE_AUTH_EXPIRE = "A007";
    // 登陆名或密码错误
    public static final String CODE_AUTH_NP_ERROR = "A008";
    // session_code未找到
    public static final String CODE_SESSION_KEY_NOT_FOUND = "A009";
    // 用户未注册
    public static final String CODE_USER_NOT_REGISTER = "A010";
    // 插入的key重复
    public static final String CODE_DUPLICATE_KEY_ERROR = "A011";
    // 接口调用太频繁
    public static final String CODE_API_FREQUENCY_TOO_HIGH = "A012";
    // 接口调用请求违法
    public static final String CODE_API_REQUEST_ILLEGAL = "A013";

    // 验证码超时
    public static final String CODE_AUTH_CODE_EXPIRE = "P105";
    // 没有收到过请求的验证码
    public static final String CODE_AUTH_CODE_NOTEXIST = "P106";
    // 远程短信接口调用失败
    public static final String CODE_AUTH_SMS_API_FAIL = "P107";
    // 验证码输入错误
    public static final String CODE_AUTH_CODE_INPUT_ERROR = "P108";

    // 数据删除失败
    public static final String CODE_DELETE_ERROR = "D001";

    // 数据修改失败
    public static final String CODE_UPDATE_ERROR = "D002";
    // 数据与原数据相同
    public static final String CODE_UPDATE_REPEAT_ERROR = "D003";
    
    /**
     * E002  注册失败,手机号已被使用
     * E003 注册失败，邮箱已被使用
     * @param args
     */

    public static void main(String[] args) {
    }
}
