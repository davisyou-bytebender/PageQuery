package com.aliyun.application.mapper.request;

import com.aliyun.application.util.PageQueryBaseRequest;
import lombok.Data;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:25 PM
 */
@Data
public class UserRepoRequest{
    private Long uid;

    private String password;

    private String telephone;

    private String username;
}
