package com.hyperj.system.service;

import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.po.SysUserPo;
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
    List<SysUserPo> getUserList(SysUserListRequest sysUserListRequest);

    /**
     * 新增用户
     * @param sysUserAddRequest
     * @return
     */
    R insertUser(SysUserAddRequest sysUserAddRequest);


    /**
     * 校验用户名是否唯一
     * @param userName
     * @return
     */
    int checkUserNameUnique(String userName);

    /**
     * 校验昵称是否唯一
     * @param nickName
     * @return
     */
     int checkNickNameUnique(String nickName);

    /**
     * 校验手机号是否唯一
     * @param mobile
     * @return
     */
     int checkMobileUnique(String mobile);

    /**
     * 校验邮箱是唯一
     * @param email
     * @return
     */
     int checkEmailUnique(String email);
}
