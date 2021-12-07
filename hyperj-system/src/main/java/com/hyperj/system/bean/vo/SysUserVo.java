package com.hyperj.system.bean.vo;

import com.hyperj.system.bean.po.SysRolePo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user
 * @author
 */
@Data
public class SysUserVo implements Serializable {
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
     * 帐号状态（1正常 0停用）
     */
    private String status;


    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否是超级管理员
     */
    private String root;

    /**
     * 角色
     */
    private List<SysRolePo> roles;


    private static final long serialVersionUID = 1L;
}
