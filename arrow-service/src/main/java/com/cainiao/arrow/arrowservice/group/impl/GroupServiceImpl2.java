package com.cainiao.arrow.arrowservice.group.impl;

import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowservice.group.GroupService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupServiceImpl2 implements GroupService {

    @Override
    public List<UserGroupDTO> queryList(){
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setName("nhq");
        List<UserGroupDTO> res = new ArrayList<>();
        res.add(userGroupDTO);
        return res;
    }

}