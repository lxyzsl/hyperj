package com.hyperj.system.dao;

import java.util.List;

public interface SysMenuDao {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long userId);
}
