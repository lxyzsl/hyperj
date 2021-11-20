package com.hyperj.app.controller.system;

import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.vo.SysUserVo;
import com.hyperj.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/user")
@Api("系统用户接口")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("获取系统用户列表")
    public R list(SysUserListRequest sysUserListRequest){
        List<SysUserVo> list = sysUserService.getUserList(sysUserListRequest);
        return R.success(list);
    }

    @ApiOperation("新增用户")
    @PostMapping("/add")
    @ResponseBody
    public R add(@Validated SysUserAddRequest sysUserAddRequest){
        sysUserService.insertUser(sysUserAddRequest);
        return null;
    }
}
