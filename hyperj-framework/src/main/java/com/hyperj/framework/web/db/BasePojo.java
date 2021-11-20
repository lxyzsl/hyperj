package com.hyperj.framework.web.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 请求参数 */
    @JsonIgnore
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
