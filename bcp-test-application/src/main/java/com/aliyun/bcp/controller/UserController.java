package com.aliyun.bcp.controller;

import javax.annotation.Resource;

import com.aliyun.bcp.common.utils.BeanCopyUtil;
import com.aliyun.bcp.common.utils.PageQueryBaseResponse;
import com.aliyun.bcp.common.utils.PageUtil;
import com.aliyun.bcp.mapper.UserMapper;
import com.aliyun.bcp.mapper.request.UserRepoRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 11:30 PM
 */
@Controller
@RequestMapping("/application")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/user")
    @ResponseBody
    public PageQueryBaseResponse<UserDTO> pageQueryUser(PageQueryUserRequest request){
        UserRepoRequest userRepoRequest = BeanCopyUtil.map(request, UserRepoRequest.class);
        return PageUtil.pagingQuery(
            request.getPage(),
            request.getSize(),
            () -> userMapper.pageQueryUser(userRepoRequest),
            UserDTO.class
        );
    }
}
