package com.cainiao.arrow.arrowservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class LogTest {

    private Logger logger = LoggerFactory.getLogger(LogTest.class);

    public void before() {
        logger.info("这是LogTest类的before方法！");
    }
    /**后置增强方法*/
    public void afterReturn() {
        logger.info("这是LogTest类的afterReturn方法！");
    }
    /**后置异常增强方法*/
    public void afterException() {
        logger.info("这是LogTest类的afterException方法！");
    }
    /**最终增强方法*/
    public void after() {
        logger.info("这是LogTest类的after方法！");
    }
    /**环绕增强方法*/
    public void around(ProceedingJoinPoint jp) {
        logger.info("这是MyLogger类的around方法！");
    }
}
