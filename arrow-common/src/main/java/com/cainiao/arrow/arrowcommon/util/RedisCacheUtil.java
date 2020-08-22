package com.cainiao.arrow.arrowcommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Map;

public class RedisCacheUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);
    private String access;
    private String secret;
    private Map<String, String> map;
    private SpringContextUtil springContextUtil;

    RedisCacheUtil(){
    }

    RedisCacheUtil(String access,String secret){
        this.access = access;
        this.secret = secret;
    }

    @PostConstruct
    public void init(){
        logger.info("this is RedisCacheUtil.init");
        logger.info("access",access);
        logger.info("secret",secret);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public SpringContextUtil getSpringContextUtil() {
        return springContextUtil;
    }

    public void setSpringContextUtil(SpringContextUtil springContextUtil) {
        this.springContextUtil = springContextUtil;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
