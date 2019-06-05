package com.trendhu.common.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendhu.common.InitService;
import com.trendhu.common.bean.http.ResponseBean;

/**
 * 所有Controller层的根基
 * 
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String postFixLog() {
        return "";
    }

    @PostConstruct
    private void makeLogs() {
        logger.info("初始化controller：" + this.getClass().getSimpleName() + "成功." + postFixLog());
    }

    /**
     * 获取统一返回对象，ResponseBean
     * 
     * @return
     */
    public ResponseBean getReturn() {
        return new ResponseBean();
    }

    /**
     * 返回请求返回对象执行的操作
     * 
     * @param bean
     *            ResponseBean 请求返回对象
     * @return ResponseBean 请求返回对象
     */
    public ResponseBean returnBean(ResponseBean bean) {
        if (bean.getData() != null && InitService.runOnDev) {
            logger.debug(bean.getData().toString());
        }
        return bean.end();
    }

    /**
     * HTTP请求对象转换成字符串，将request输出为String
     * 
     * @param request
     *            HttpServletRequest HTTP请求对象
     * @return String 转换成的字符串
     * @throws IOException 
     */
    public String readRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
        } finally {
            request.getReader().close();
        }
        return sb.toString();
    }

}
