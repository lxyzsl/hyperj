package com.hyperj.framework.web.page;

import com.hyperj.common.utils.ServletUtils;

public class TableSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "current";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM) != null ? ServletUtils.getParameterToInt(PAGE_NUM) :1);
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE) != null ? ServletUtils.getParameterToInt(PAGE_SIZE) :10);
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
