package com.hyperj.system.bean.request;

import com.hyperj.common.bean.ListRequestBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * sys_user
 * @author
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuListRequest  extends ListRequestBean {

    /** 菜单名称 */
    private String menuName;

    /**
     * 帐号状态（1正常 0停用）
     */
    @ApiModelProperty(value="帐号状态（1正常 0停用）",name="status",allowableValues="0,1")
    private String status;

    /** 显示状态（1显示 0隐藏） */
    @ApiModelProperty(value="显示状态（1显示 0隐藏）",name="visible",allowableValues="0,1")
    private String visible;


}
