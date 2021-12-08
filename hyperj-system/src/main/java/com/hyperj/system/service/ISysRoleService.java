package com.hyperj.system.service;

import com.hyperj.system.bean.po.SysRolePo;
import com.hyperj.system.bean.request.SysRoleListRequest;

import java.util.List;
import java.util.Set;

public interface ISysRoleService {

    Set<String> selectRoleKeys(Long userId);

    List<SysRolePo> selectRoleList(SysRoleListRequest sysRoleListRequest);
}
