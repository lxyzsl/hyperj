package com.hyperj.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ListRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="页码")
    private int current;

    @ApiModelProperty(value="分页大小")
    private int pageSize;

    @ApiModelProperty(value="排序字段")
    private String orderByColumn;

    @ApiModelProperty(value="排序规则",name="isAsc",allowableValues="asc,desc")
    private String isAsc;

    /** 其他参数 */
    @ApiModelProperty(hidden = true)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }
}
