package com.cainiao.arrow.arrowstart.controller;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowservice.group.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @RequestMapping(value = "/helloQueue",method = RequestMethod.GET)
    public String getStr(@RequestParam(required = false ,name = "param1") String param){
        try {
            String res = param ==null?"":"ffff";
//            GroupServiceImpl groupService = SpringContextUtil.getBean("groupServiceImpl");
            List<UserGroupDTO> list = groupServiceImpl.queryList();
            return JSON.toJSONString(list);
        }catch (Exception e){
            return JSON.toJSONString(e.getMessage());
        }
    }

}
