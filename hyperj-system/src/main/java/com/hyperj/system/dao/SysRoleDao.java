package com.hyperj.system.dao;

import com.hyperj.system.bean.po.SysRolePo;
import com.hyperj.system.bean.request.SysRoleListRequest;

import java.util.List;

public interface SysRoleDao {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRolePo> selectRolesByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRolePo selectRoleById(Long roleId);

    /**
     * 获取角色列表
     * @return
     */
    List<SysRolePo> selectRoleList(SysRoleListRequest sysRoleListRequest);


}
