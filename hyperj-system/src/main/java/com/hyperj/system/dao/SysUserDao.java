package com.hyperj.system.dao;

import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.po.SysUserPo;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 校验昵称是否唯一
     * @param nickName
     * @return
     */
    @Select("select count(1) from sys_user where nick_name=#{nickName} limit 1")
    int checkNickNameUnique(String nickName);

    /**
     * 校验手机号是否唯一
     * @param mobile
     * @return
     */
    @Select("select count(1) from sys_user where mobile=#{mobile} limit 1")
    int checkMobileUnique(String mobile);

    /**
     * 校验邮箱是否唯一
     * @param email
     * @return
     */
    @Select("select count(1) from sys_user where email=#{email} limit 1")
    int checkEmailUnique(String email);

    /**
     * 获取用户信息
     */
    SysUserPo getUserInfo(Long userId);

    /**
     * 修改用户信息
     */
    int updateUser(SysUserPo sysUserPo);


    /**
     * 删除用户（假删除）
     */
    @Select("update sys_user set removed = 1 where user_id = #{userId}")
    void deleteUser(Long userId);

    /**
     * 设置账号状态
     */
    @Select("update sys_user set status = #{status} where user_id = #{userId}")
    void setStatus(Long userId,String status);
}
