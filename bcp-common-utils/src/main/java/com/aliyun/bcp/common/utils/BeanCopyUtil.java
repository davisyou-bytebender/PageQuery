package com.aliyun.bcp.common.utils;

import java.util.Collection;
import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * bean拷贝
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 11:14 PM
 */
public class BeanCopyUtil {
    /**
     * 默认字段工厂
     */
    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * 默认字段实例
     */
    private static final MapperFacade MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();


    /**
     * 映射集合（默认字段）
     *
     * @param toClass 映射类对象
     * @param data    数据（集合）
     * @return 映射类对象
     */
    public static <E, T> List<E> mapAsList(Collection<T> data, Class<E> toClass) {
        return MAPPER_FACADE.mapAsList(data, toClass);
    }

    /**
     * 映射实体（默认字段）
     *
     * @param toClass 映射类对象
     * @param data    数据（对象）
     * @return 映射类对象
     */
    public static <E, T> E map(T data, Class<E> toClass) {
        return MAPPER_FACADE.map(data, toClass);
    }

}
