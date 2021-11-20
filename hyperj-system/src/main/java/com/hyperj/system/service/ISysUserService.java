package com.hyperj.system.service;

import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.vo.SysUserVo;

import java.util.List;

public interface ISysUserService {

    /**
     * 查询用户列表
     * @param sysUserListRequest
     * @return
     */
    List<SysUserVo> getUserList(SysUserListRequest sysUserListRequest);

    /**
     * 新增用户
     * @param sysUserAddRequest
     * @return
     */
    int insertUser(SysUserAddRequest sysUserAddRequest);


    /**
     * 校验用户名是否唯一
     * @param userName
     * @return
     */
    int checkUserNameUnique(String userName);
}
