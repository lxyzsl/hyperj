package com.hyperj.system.service.impl;

import com.hyperj.common.utils.StringUtils;
import com.hyperj.system.bean.po.SysRolePo;
import com.hyperj.system.bean.request.SysRoleListRequest;
import com.hyperj.system.dao.SysRoleDao;
import com.hyperj.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<SysRolePo> perms = sysRoleDao.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRolePo perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 获取角色列表
     */
    @Override
    public List<SysRolePo> selectRoleList(SysRoleListRequest sysRoleListRequest) {
        return sysRoleDao.selectRoleList(sysRoleListRequest);
    }


}
