package com.alibaba.csp.sentinel.dashboard.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title:
 * @BelongPackage com.alibaba.csp.sentinel.dashboard.aspect
 * @Description:
 * @Author: 贺兆嘉
 * @Date: 2021/9/8 16:55
 */
@Aspect
@Component
public class RequestAspect {

    @Value("${nacos.refresh.internal}")
    private Long nacosRefreshInternal;


    /**
     * 切面定义
     */
    @Pointcut("execution(* com.alibaba.csp.sentinel.dashboard..DynamicRuleProvider+.getRules(*))")
    public void executeService() {
    }


    /**
     * nacos配置更新后延缓获取最新配置的时间，保证配置可以自动刷新
     *
     * @param pjp aop输入
     * @return 返回
     * @throws Throwable 异常抛出
     */
    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Thread.sleep(nacosRefreshInternal);
        return pjp.proceed();
    }
}