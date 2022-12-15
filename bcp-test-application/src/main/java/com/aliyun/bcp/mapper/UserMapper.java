package com.aliyun.bcp.mapper;

import java.util.List;

import com.aliyun.bcp.mapper.dataobject.UserDO;
import com.aliyun.bcp.mapper.request.UserRepoRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:22 PM
 */
@Mapper
public interface UserMapper {

    /**
     * 分页查询用户信息
     * @param request
     * @return
     */
    List<UserDO> pageQueryUser(UserRepoRequest request);

}
