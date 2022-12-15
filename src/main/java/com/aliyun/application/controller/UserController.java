package com.aliyun.application.controller;

import java.util.List;

import javax.annotation.Resource;

import com.aliyun.application.mapper.UserMapper;
import com.aliyun.application.mapper.request.UserRepoRequest;
import com.aliyun.application.util.BeanCopyUtil;
import com.aliyun.application.util.PageQueryBaseResponse;
import com.aliyun.application.util.PageUtil;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
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
