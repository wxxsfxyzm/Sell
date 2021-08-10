package com.carlyu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class ProductCategoryServiceAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.carlyu.service.impl.ProductCategoryServiceImpl.*(..))")
    public void log() {// 切入点方法应为空或在另一个文件(MyAspectJ.java)中定义
    }

    /**
     * 前置通知
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("打印日志=============");
        // RequestAttributes(接口) ---> ServletRequestAttributes(接口的实现类)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // import javax.servlet.http.HttpServletRequest;
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();


        // url
        log.info("url={}", request.getRequestURL());

        // method
        log.info("method={}", request.getMethod());

        // ip
        log.info("ip={}", request.getRemoteAddr());

        // 类   类中方法
        // import org.aspectj.lang.Signature;
        Signature signature = joinPoint.getSignature();
        log.info("class_method={}", signature.getDeclaringTypeName() + "." + signature.getName());

        // 方法的参数
        log.info("methodArgs={}", signature.getDeclaringTypeName() + "." + joinPoint.getArgs());
    }

    /**
     * 后置通知
     */
    @After("log()")
    public void doAfter() {
        // log.info("=================================")
    }

    /**
     * 带返回值的后置通知
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        log.info("response={}", object.toString());
        log.info("日志结束===============");
    }
}
