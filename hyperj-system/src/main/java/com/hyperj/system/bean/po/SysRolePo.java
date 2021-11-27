package com.hyperj.system.bean.po;

import com.hyperj.common.bean.BasePoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SysRolePo extends BasePoBean {

    /**
     * 角色id
     */
    private long roleId;

    /**
     * 角色中文名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /** 角色排序 */
    private String roleSort;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
    private String dataScope;

    /** 角色状态（1正常 0停用） */
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String removed;


}
