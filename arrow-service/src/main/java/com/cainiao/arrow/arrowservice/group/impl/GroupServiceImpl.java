package com.cainiao.arrow.arrowservice.group.impl;

import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowservice.group.GroupService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class GroupServiceImpl implements GroupService {

    @Override
    public List<UserGroupDTO> queryList(){
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setName("nhq");
        List<UserGroupDTO> res = new ArrayList<>();
        res.add(userGroupDTO);
        return res;
    }

}
