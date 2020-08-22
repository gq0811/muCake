package com.cainiao.arrow.arrowstart.controller;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowcommon.util.RedisCacheUtil;
import com.cainiao.arrow.arrowcommon.util.SpringContextUtil;
import com.cainiao.arrow.arrowservice.aop.AutoConfigService;
import com.cainiao.arrow.arrowservice.group.GroupService;
import com.cainiao.arrow.arrowservice.group.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private GroupService groupServiceImpl;
    @Resource
    private AutoConfigService autoConfigService;

    @RequestMapping(value = "/helloQueue",method = RequestMethod.GET)
    public String getStr(@RequestParam(required = false ,name = "param1") String param){
        try {
            String res = param ==null?"":"ffff";
            RedisCacheUtil redisCacheUtil = SpringContextUtil.getBean("redisCacheUtil");
            autoConfigService.add();
            return JSON.toJSONString(redisCacheUtil.getAccess());
        }catch (Exception e){
            return JSON.toJSONString(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");

        RedisCacheUtil redisCacheUtil =  (RedisCacheUtil)context.getBean("redisCacheUtil");
        System.out.printf(redisCacheUtil.getAccess());
    }
}
