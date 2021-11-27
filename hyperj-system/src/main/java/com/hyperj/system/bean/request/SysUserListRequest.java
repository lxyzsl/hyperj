package com.hyperj.system.bean.request;

import com.hyperj.common.bean.ListRequestBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * sys_user
 * @author
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListRequest extends ListRequestBean {

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 帐号状态（1正常 0停用）
     */
    @ApiModelProperty(value="帐号状态（1正常 0停用）",name="status",allowableValues="0,1")
    private String status;



    /**
     * 登录时间范围
     */
    @ApiModelProperty(value="登录时间(end)")
    private String loginEndTime;

    /**
     * 登录时间范围
     */
    @ApiModelProperty(value="登录时间(start)")
    private String loginStartTime;


    private static final long serialVersionUID = 1L;
}
