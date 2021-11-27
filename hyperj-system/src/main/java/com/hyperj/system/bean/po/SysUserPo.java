package com.hyperj.system.bean.po;

import java.util.Date;

import com.hyperj.common.bean.BasePoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * sys_user
 * @author
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserPo extends BasePoBean {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别（0未知 1男 2女 ）
     */
    private String gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;


    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（1正常 0停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String removed;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 是否是超级管理员
     */
    private String root;

    private static final long serialVersionUID = 1L;
}
