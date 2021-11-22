package com.hyperj.app.controller.system;

import com.hyperj.framework.web.controller.BaseController;
import com.hyperj.framework.web.page.TableDataInfo;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.vo.SysUserVo;
import com.hyperj.system.convert.SysUserConvert;
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
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserConvert sysUserConvert;

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("获取系统用户列表")
    public TableDataInfo<SysUserVo> list(SysUserListRequest sysUserListRequest){
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        startPage();
        List<SysUserPo> list = sysUserService.getUserList(sysUserListRequest);
        return getDataTable(list,sysUserConvert.sysUserVoList(list));
    }

    @ApiOperation("新增用户")
    @PostMapping("/add")
    @ResponseBody
    public R add(@Validated SysUserAddRequest sysUserAddRequest){
        return sysUserService.insertUser(sysUserAddRequest);
    }
}
