package com.aliyun.bcp.mapper.dataobject;

import lombok.Data;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:23 PM
 */
@Data
public class UserDO {

    private Long uid;

    private String password;

    private String telephone;

    private String username;

}
