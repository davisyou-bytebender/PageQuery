package com.aliyun.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.aliyun.application.util.PageQueryBaseRequest.Order;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * 分页工具类
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 10:46 PM
 */
public class PageUtil {

    private PageUtil() {
        throw new UnsupportedOperationException("静态工具类不允许实例化");
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，然后返回统一的翻页结果
     *
     * @param page      页码
     * @param size      分页大小
     * @param withCount 是否查询总记录数
     * @param queryFun  查询回调，执行具体的全量查询逻辑
     * @return 统一翻页结果
     */
    public static <T> PageQueryBaseResponse<T> pagingQuery(int page, int size, boolean withCount,
        Supplier<List<T>> queryFun) {
        PageQueryBaseRequest queryDTO = new PageQueryBaseRequest();
        queryDTO.setWithCount(withCount);
        queryDTO.setPage(page);
        queryDTO.setSize(size);
        return pagingQuery(queryDTO, queryFun);
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，然后返回统一的翻页结果
     *
     * @param page     页码
     * @param size     分页大小
     * @param queryFun 查询回调，执行具体的全量查询逻辑
     * @return 统一翻页结果
     */
    public static <T> PageQueryBaseResponse<T> pagingQuery(int page, int size, Supplier<List<T>> queryFun) {
        return pagingQuery(page, size, true, queryFun);
    }


    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，然后返回统一的翻页结果
     *
     * @param queryDTO 分页查询条件基类
     * @param queryFun 查询回调，执行具体的全量查询逻辑
     * @return 统一翻页结果
     */
    public static <T> PageQueryBaseResponse<T> pagingQuery(PageQueryBaseRequest queryDTO, Supplier<List<T>> queryFun) {
        Page<T> page = PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize(),
            defaultIfNull(queryDTO.getWithCount(), true));
        try {
            setOrder(queryDTO, page);
            List<T> list = queryFun.get();
            requirePage(list);
            PageInfo<T> pageInfo = PageInfo.of(list);
            return PageQueryBaseResponse.pagingResponse(list, (int)pageInfo.getStartRow(),
                pageInfo.getPageSize(), (int) pageInfo.getTotal());
        } finally {
            PageHelper.clearPage();
        }
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，根据提供的转换器将结果转换，并返回统一的翻页结果
     *
     * @param queryDTO  分页查询条件基类
     * @param queryFun  查询回调，执行具体的全量查询逻辑
     * @param converter 转换器，将查出来的数据结果转换为想要的类型
     * @return 统一翻页结果
     */
    public static <T, R> PageQueryBaseResponse<R> pagingQuery(PageQueryBaseRequest queryDTO, Supplier<List<T>> queryFun,
        Function<List<T>, List<R>> converter) {
        Page<T> page = PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize(),
            defaultIfNull(queryDTO.getWithCount(), true));
        try {
            setOrder(queryDTO, page);
            List<T> list = queryFun.get();
            requirePage(list);
            PageInfo<T> pageInfo = PageInfo.of(list);
            List<R> result = converter.apply(pageInfo.getList());
            return PageQueryBaseResponse.pagingResponse(result, (int)pageInfo.getStartRow(),
                pageInfo.getPageSize(), (int) pageInfo.getTotal());
        } finally {
            PageHelper.clearPage();
        }
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，根据提供的转换器将结果转换，并返回统一的翻页结果
     *
     * @param page      页码
     * @param size      分页大小
     * @param queryFun  查询回调，执行具体的全量查询逻辑
     * @param withCount 是否查询总记录数
     * @param converter 转换器，将查出来的数据结果转换为想要的类型
     * @return 统一翻页结果
     */
    public static <T, R> PageQueryBaseResponse<R> pagingQuery(int page, int size, boolean withCount,
        Supplier<List<T>> queryFun, Function<List<T>, List<R>> converter) {
        PageQueryBaseRequest queryDTO = new PageQueryBaseRequest();
        queryDTO.setWithCount(withCount);
        queryDTO.setPage(page);
        queryDTO.setSize(size);
        return pagingQuery(queryDTO, queryFun, converter);
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，根据提供的转换器将结果转换，并返回统一的翻页结果
     *
     * @param queryFun    查询回调，执行具体的全量查询逻辑
     * @param targetClazz 目标类型，查询出来的对象将会被映射为这个类型的对象
     * @return 统一翻页结果
     */
    public static <T, R> PageQueryBaseResponse<R> pagingQuery(int page, int size, Supplier<List<T>> queryFun,
        Class<R> targetClazz) {
        return pagingQuery(page, size, true, queryFun, t -> BeanCopyUtil.mapAsList(t, targetClazz));
    }

    /**
     * 分页查询，将全量的查询逻辑自动添加翻页逻辑，根据提供的转换器将结果转换，并返回统一的翻页结果
     *
     * @param queryFun    查询回调，执行具体的全量查询逻辑
     * @param targetClazz 目标类型，查询出来的对象将会被映射为这个类型的对象
     * @return 统一翻页结果
     */
    public static <T, R> PageQueryBaseResponse<R> pagingQuery(PageQueryBaseRequest queryDTO, Supplier<List<T>> queryFun,
        Class<R> targetClazz) {
        Page<T> page = PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize(),
            defaultIfNull(queryDTO.getWithCount(), true));
        try {
            setOrder(queryDTO, page);
            List<T> list = queryFun.get();
            requirePage(list);
            PageInfo<T> pageInfo = PageInfo.of(list);
            List<R> result = BeanCopyUtil.mapAsList(pageInfo.getList(), targetClazz);
            return PageQueryBaseResponse.pagingResponse(result, (int) pageInfo.getStartRow(),
                pageInfo.getPageSize(), (int) pageInfo.getTotal());
        } finally {
            PageHelper.clearPage();
        }
    }

    /**
     * 分页查出来的结果必须是一个Page对象，否则抛出异常
     */
    private static <T> void requirePage(List<T> list) {
        if (!(list instanceof Page)) {
            throw new IllegalStateException("查询方法必须返回从数据库里直接查出来的列表，请勿对它做任何处理");
        }
    }

    private static <T> void setOrder(PageQueryBaseRequest queryDTO, Page<T> page) {
        if (StringUtils.isNotBlank(queryDTO.getOrderBy())) {
            page.setOrderBy(StringManipulateUtil.camelToSnakeCase(queryDTO.getOrderBy()) + " " + ObjectUtils
                .defaultIfNull(queryDTO.getSortType(), ""));
        } else if (CollectionUtils.isNotEmpty(queryDTO.getOrderList())) {
            List<String> orderList = new ArrayList<>();
            for (Order order : queryDTO.getOrderList()) {
                if (StringUtils.isBlank(order.getFieldName())) {
                    continue;
                }
                if (StringUtils.isNotBlank(order.getSortType()) &&
                    !PageQueryBaseRequest.ORDER_ASC.equalsIgnoreCase(order.getSortType()) &&
                    !PageQueryBaseRequest.ORDER_DESC.equalsIgnoreCase(order.getSortType())) {
                    continue;
                }
                String filed = StringManipulateUtil.camelToSnakeCase(order.getFieldName());
                String sortType = StringUtils.isBlank(order.getSortType()) ? PageQueryBaseRequest.ORDER_ASC
                    : order.getSortType();
                orderList.add(filed + " " + sortType);
            }
            String orderColumn = Joiner.on(",").skipNulls().join(orderList);
            page.setOrderBy(orderColumn);
        }
    }
}
