package com.trendhu.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 项目的初始化服务，完成一些系统初始化的工作
 * 
 * @author huyazhou
 * @version 2018-11-22 15:27
 */
@Service
@SuppressWarnings("initReboot")
public class InitService {
    private static final Logger logger = LoggerFactory.getLogger(InitService.class);
    
    public static final boolean runOnDev = true;

}
