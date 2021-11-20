package com.hyperj.system.dao;

import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.po.SysUserPo;

import java.util.List;


public interface SysUserDao {

    /**
     * 查询用户列表
     * @param sysUserListRequest
     * @return
     */
    List<SysUserPo> selectUserList(SysUserListRequest sysUserListRequest);

    /**
     * 新增用户
     * @param sysUserPo
     * @return
     */
    int insertUser(SysUserPo sysUserPo);

    /**
     * 校验用户名称是否唯一
     * @param userName
     * @return
     */
    int checkUserNameUnique(String userName);

}
