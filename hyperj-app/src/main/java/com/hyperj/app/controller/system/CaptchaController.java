package com.hyperj.app.controller.system;

import com.hyperj.common.enums.CacheType;
import com.hyperj.common.utils.Captcha;
import com.hyperj.common.utils.redis.RedisStringCache;
import com.hyperj.common.utils.uuid.Uuid;
import com.hyperj.framework.web.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags="登录验证码")
@RestController
public class CaptchaController {

    /**
     * 生成验证码
     */
    @ApiOperation("获取验证码")
    @GetMapping("/captchaImage")
    public R getCode() throws IOException {
        // 1、生成验证码（长120，高40，4个字符 噪点+线条的图片 ）
        Captcha captcha = new Captcha(120,40,4,10);
        // 2、将验证码<ID,验证码数值>放入缓存
        // 这里的ID是uuid，因为jdk自带的uuid效率不高，且在高并发时候任然会产生重复。所以这里用到了Twitter_Snowflake
        String uuid = String.valueOf(Uuid.getInstance().getUUID());
        RedisStringCache.cache(uuid,captcha.getCode(), CacheType.CAPTCHA);
//        // 3、使用base64编码图片，并返回给前台
        return R.success().put("id",uuid).put("imageBase64",captcha.getBase64ByteStr());
    }
}
