package com.hyperj.system.bean.request;

import com.hyperj.framework.web.db.BasePojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * sys_user
 * @author
 */
@Data
public class SysUserListRequest extends BasePojo {

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


    private static final long serialVersionUID = 1L;
}
