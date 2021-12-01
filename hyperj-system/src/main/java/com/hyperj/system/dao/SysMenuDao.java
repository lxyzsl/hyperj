package com.hyperj.system.dao;

import com.hyperj.system.bean.po.SysMenuPo;
import com.hyperj.system.bean.request.SysMenuListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuDao {

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenuPo> selectMenuList(SysMenuListRequest menu);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @return 菜单列表
     */
    public List<SysMenuPo> selectMenuListByUserId(SysMenuListRequest menu);
}
