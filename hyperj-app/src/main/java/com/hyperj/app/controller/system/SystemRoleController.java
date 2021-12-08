package com.hyperj.app.controller.system;

import com.hyperj.framework.web.controller.BaseController;
import com.hyperj.framework.web.page.TableDataInfo;
import com.hyperj.system.bean.po.SysRolePo;
import com.hyperj.system.bean.request.SysRoleListRequest;
import com.hyperj.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@Api(tags="系统角色接口")
public class SystemRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("system:role:list")
    @GetMapping("/list")
    @ApiOperation("获取角色列表")
    public TableDataInfo list(SysRoleListRequest sysRoleListRequest)
    {
        startPage();
        List<SysRolePo> list = roleService.selectRoleList(sysRoleListRequest);
        return getDataTable(list);
    }

}
