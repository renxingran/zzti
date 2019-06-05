package com.trendhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 项目启动入口
 */
@SpringBootApplication
@PropertySources(@PropertySource("classpath:config.properties")) // 指定配置文件
public class QARebootApplication {

	public static void main(String[] args) {
        SpringApplication.run(QARebootApplication.class, args);
    }
}