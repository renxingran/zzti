package com.trendhu.common.bean.http;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 统一返回对象，实现对请求返回结果的格式统一
 * 
 */
@Data
public class ResponseBean {
    private static final List<Object> nullList = new ArrayList<>();

    private String code = "A001"; // 编码
    private String errMsg = ""; // 报错内容
    private Object data; // 数据

    /**
     * 当返回即将结束时对 ResponseBean 进行空状态修改
     * 
     * @return ResponseBean
     */
    public ResponseBean end() {
        if (this.data == null) {
            this.data = nullList;
        }
        if (!this.code.equals("A001") && this.errMsg.length() < 1) {
            this.errMsg += "若您操作无误，请联系网站管理人员";
        }
        return this;
    }

    /**
     * 同时对 编码 和 报错内容 进行赋值
     * 
     * @param code
     *            String 编码
     * @param errMsg
     *            String 报错内容
     */
    public void setCodeAndErrMsg(String code,
                                 String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

}
