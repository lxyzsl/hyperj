package com.hyperj.system.service.impl;

import com.hyperj.common.utils.StringUtils;
import com.hyperj.system.bean.po.SysMenuPo;
import com.hyperj.system.bean.request.SysMenuListRequest;
import com.hyperj.system.dao.SysMenuDao;
import com.hyperj.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        List<String> perms = sysMenuDao.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    @Override
    public List<SysMenuPo> selectMenuList(SysMenuListRequest menu,Long userId) {
        List<SysMenuPo> menuList;
        // 管理员显示所有菜单信息
        if (userId != null && 1L == userId)
        {
            menuList = sysMenuDao.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId",userId);
            menuList = sysMenuDao.selectMenuListByUserId(menu);
        }
        return menuList;
    }


}
