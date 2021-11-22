package com.hyperj.framework.web.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author plastic
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TableDataInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean success = true;

    /** 总记录数 */
    private long total;

    /** 当前页数 */
    private int current;

    /** 每页显示多少数据 */
    private int pageSize;

    /** 列表数据 */
    private List<T> data;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<T> list, int total)
    {
        this.data = list;
        this.total = total;
    }

}
