package com.hyperj.system.bean.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SysLoginRequest  implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message="请输入用户名")
    private String userName;

    @NotBlank(message="请输入密码")
    private String password;

    @NotBlank(message="请输入验证码")
    private String captcha;

    @NotBlank(message="验证码ID不得为空")
    private String captchaId;
}
