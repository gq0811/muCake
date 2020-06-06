package com.cainiao.arrow.arrowstart.controller;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowservice.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private GroupService groupService;

    @RequestMapping(value = "/helloQueue",method = RequestMethod.GET)
    public String getStr(@RequestParam(required = false ,name = "param1") String param){
        String res = param ==null?"":"ffff";
        List<UserGroupDTO> list = groupService.queryList();
        return JSON.toJSONString(list);
    }

}
