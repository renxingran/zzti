package com.trendhu.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 
 * @author huyazhou
 * @version 2018-11-21 17:40
 */
//@Aspect  // 使用@Aspect注解声明一个切面
//@Component
public class BaseDaoAspect {

//    @Pointcut("execution(com.trendhu.common.dao.imp.BaseDaoImp.insert(..))")
//    public void insertPointCut() {}
//    
//    @Before("insertPointCut()")
//    public Object insertBefore(ProceedingJoinPoint point) throws Throwable {
//        Object result = point.proceed();
//        return result;
//    }
    
}
