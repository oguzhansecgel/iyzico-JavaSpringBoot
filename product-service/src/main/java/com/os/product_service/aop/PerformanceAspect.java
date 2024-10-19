package com.os.product_service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    // @Before : Metot devreye girmeden önce çalışır
    // @After : Metot devreye girdikten sonra çalışır
    // @Around : Metot devreye girmeden ve bittikten sonra çalışır
    // AfterReturning : Metot başarılı olduktan sonra çalışır
    // AfterThrowing : Metot exception döndükten sonra çalışır

    @Around("execution(* com.os.product_service.service.impl.CategoryServiceImpl.createCategory(..))")
    public Object measureGetAllExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info(joinPoint.getSignature() + " executed in " + duration + "ms to create category");

        return result;
    }
}
