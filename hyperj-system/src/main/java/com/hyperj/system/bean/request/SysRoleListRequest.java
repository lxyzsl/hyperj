package com.hyperj.system.bean.request;

import com.hyperj.common.bean.ListRequestBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleListRequest  extends ListRequestBean {

    /**
     * 角色中文名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /** 角色状态（1正常 0停用） */
    @ApiModelProperty(value="帐号状态（1正常 0停用）",name="status",allowableValues="0,1")
    private String status;

    /**
     * 创建时间范围
     */
    @ApiModelProperty(value="创建时间(end)")
    private String createdEndTime;

    /**
     * 创建时间范围
     */
    @ApiModelProperty(value="创建时间(start)")
    private String createdStartTime;

}
