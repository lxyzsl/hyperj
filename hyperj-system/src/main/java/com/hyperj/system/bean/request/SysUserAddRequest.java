package com.hyperj.system.bean.request;

import com.hyperj.common.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * sys_user
 * @author
 */
@Data
public class SysUserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    @NotBlank(message = "登录账号不能为空")
    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
    private String userName;

    /**
     * 用户昵称
     */
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    @Phone
    private String mobile;

    /**
     * 用户性别（0未知 1男 2女 ）
     */
    @ApiModelProperty(value="用户性别（0未知 1男 2女 ）",name="gender",allowableValues="0,1,2")
    private String gender = "0";

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;


    /**
     * 备注
     */
    @ApiModelProperty(hidden=true)
    private String remark;




}
