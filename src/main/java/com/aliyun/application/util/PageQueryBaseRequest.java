package com.aliyun.application.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分页查询参数基础Request
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:28 PM
 */
public class PageQueryBaseRequest {
    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";
    /**
     * 默认每页数据量
     */
    private static final int DEFAULT_DB_PAGE_SIZE = 10;
    /**
     * 每页最大数量
     */
    private static final int MAX_DB_PAGE_SIZE = 1000;

    /**
     * 默认每页数量
     */
    private Integer size = DEFAULT_DB_PAGE_SIZE;

    /**
     * 当前页
     */
    private Integer page = 1;

    /**
     * 排序类型:ASC, DESC;
     */
    private String sortType = ORDER_DESC;
    /**
     * 单个排序字段
     */
    private String orderBy;

    /**
     * 是否包含问题
     * 有些查询可以不需要返回总量以减少性能损耗
     */
    private Boolean withCount;
    /**
     * 多字段排序
     */
    @Getter
    @Setter
    private List<Order> orderList;


    /**
     * 便于接收前端字段的多字段排序，格式 field|sortType,field|sortType
     */
    @Getter
    private String orderFieldAndSortTypes;

    public void setOrderFieldAndSortTypes(String orderFieldAndSortTypes) {
        if (StringUtils.isNotBlank(orderFieldAndSortTypes)) {
            orderFieldAndSortTypes = orderFieldAndSortTypes.trim();
            if (orderFieldAndSortTypes.matches(".*\\s.*")) {
                // 为防止SQL注入，排序字段不允许出现空白字符
                return;
            }
            // field|sortType,field|sortType
            orderList = Arrays.stream(orderFieldAndSortTypes.trim().split(",")).map(s -> {
                String[] ss = s.split("\\|");
                return new Order(ss[0], ss.length > 1 ? ss[1] : "DESC");
            }).collect(Collectors.toList());
        }
        this.orderFieldAndSortTypes = orderFieldAndSortTypes;
    }

    public int getSize() {
        return size == null ? DEFAULT_DB_PAGE_SIZE : size;
    }

    /**
     * 设置每页的查询行数，最大为1000
     */
    public void setSize(Integer size) {
        if (size == null || size <= 0) {
            size = DEFAULT_DB_PAGE_SIZE;
        } else if (size > MAX_DB_PAGE_SIZE) {
            size = MAX_DB_PAGE_SIZE;
        }
        this.size = size;
    }

    /**
     * 设置不限大小的每页数量
     * 一些导出场景需要突破最大记录数的限制，可以在后台手动调用此方法设置任意大小的分页大小
     */
    public void withUnlimitedSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page == null ? 1 : page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            page = 1;
        }
        this.page = page;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = removeWhiteSpace(sortType);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Boolean getWithCount() {
        return withCount;
    }

    public void setWithCount(Boolean withCount) {
        this.withCount = withCount;
    }

    /**
     * 防止SQL注入，只支持单一字段排序
     * 支持多字段排序请使用 orderFieldAndSortTypes
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = removeWhiteSpace(orderBy);
    }

    private String removeWhiteSpace(String str) {
            return str == null ? null : str.replaceAll("\\s", "");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * 字段排序对象
     */
    @Getter
    @Setter
    public static final class Order {
        /**
         * 数据库排序字段名称，外部传驼峰格式，内部转换为下划线格式
         */
        private String fieldName;
        /**
         * 排序类型:ASC, DESC;
         * 默认：ASC
         */
        private String sortType;

        public Order(String fieldName, String sortType) {
            this.fieldName = fieldName;
            this.sortType = sortType;
        }
    }
}