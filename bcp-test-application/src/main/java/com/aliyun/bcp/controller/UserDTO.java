package com.aliyun.bcp.controller;

import lombok.Data;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 11:32 PM
 */
@Data
public class UserDTO {

    private Long uid;

    private String password;

    private String telephone;

    private String username;

}
