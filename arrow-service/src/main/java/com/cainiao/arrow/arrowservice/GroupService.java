package com.cainiao.arrow.arrowservice;

import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupService {

    public List<UserGroupDTO> queryList(){
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setName("my name");
        List<UserGroupDTO> res = new ArrayList<>();
        res.add(userGroupDTO);
        return res;
    }

}
