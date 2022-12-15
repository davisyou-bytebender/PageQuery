package com.aliyun.bcp.common.utils;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 分页查询返回值基类
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:41 PM
 */
@Data
public class PageQueryBaseResponse<T> {

    private Pagination pagination;
    private List<T> items;

    public PageQueryBaseResponse() {
    }

    public static <T> PageQueryBaseResponse<T> pagingResponse(List<T> items, int startRow, int pageSize,
        int totalRecordCount) {
        PageQueryBaseResponse<T> pagingResponse = new PageQueryBaseResponse<>();
        Pagination pagination = parsePagination(startRow, totalRecordCount, pageSize);
        pagingResponse.setItems(items);
        pagingResponse.setPagination(pagination);
        return pagingResponse;
    }

    private static Pagination parsePagination(int startRow, int totalCount, int pageSize) {
        int pageCount = 0;
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }

        Pagination pagination = new Pagination();
        pagination.setCurrent(startRow / pageSize + 1);
        pagination.setTotalItems(totalCount);
        pagination.setSize(pageSize);
        pagination.setTotalPages(pageCount);
        return pagination;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Data
    public static class Pagination {
        public static final String ORDER_ASC = "asc";
        public static final String ORDER_DESC = "desc";
        private int current = 1;
        private int size = 10;
        private int totalItems;
        private int totalPages;
        private List<String> orderBy;
        private Map<String, String> order;

        public Pagination() {
        }
    }
}
