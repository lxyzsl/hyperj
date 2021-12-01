package com.hyperj.system.service;

import com.hyperj.system.bean.po.SysMenuPo;
import com.hyperj.system.bean.request.SysMenuListRequest;

import java.util.List;
import java.util.Set;

public interface ISysMenuService {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenuPo> selectMenuList(SysMenuListRequest menu,Long userId);
}
