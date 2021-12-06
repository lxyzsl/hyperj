package com.hyperj.app.controller.system;

import com.hyperj.framework.web.controller.BaseController;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.po.SysMenuPo;
import com.hyperj.system.bean.request.SysMenuListRequest;
import com.hyperj.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Api(tags="菜单接口")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService  sysMenuService;

    /**
     * 获取菜单列表
     */
    @RequiresPermissions("system:menu:list")
    @ApiOperation("获取菜单列表")
    @ResponseBody
    @GetMapping("/list")
    public R list(SysMenuListRequest menu)
    {
        List<SysMenuPo> menus = sysMenuService.selectMenuList(menu, getUserId());
        return R.success(menus);
    }
}
