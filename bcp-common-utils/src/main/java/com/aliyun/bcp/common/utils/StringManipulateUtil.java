package com.aliyun.bcp.common.utils;

import com.google.common.base.CaseFormat;

/**
 * 字符串处理类
 *
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 11:10 PM
 */
public class StringManipulateUtil {

    /**
     * 驼峰转蛇形变量命名
     *
     * @param name
     * @return
     */
    public static String camelToSnakeCase(String name) {
        return null == name ? name : CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
}
