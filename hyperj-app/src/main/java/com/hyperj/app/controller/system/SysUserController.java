package com.hyperj.app.controller.system;

import com.hyperj.framework.web.controller.BaseController;
import com.hyperj.framework.web.page.TableDataInfo;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.bean.request.SysUserEditRequest;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.bean.vo.SysUserVo;
import com.hyperj.system.convert.SysUserConvert;
import com.hyperj.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/user")
@Api(tags="系统用户接口")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserConvert sysUserConvert;

    @GetMapping("/list")
    @RequiresPermissions("system:user:list")
    @ResponseBody
    @ApiOperation("获取系统用户列表")
    public TableDataInfo<SysUserVo> list(SysUserListRequest sysUserListRequest){
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        startPage();
        List<SysUserPo> list = sysUserService.getUserList(sysUserListRequest);
        return getDataTable(list,sysUserConvert.sysUserVoList(list));
    }

    @ApiOperation("新增用户")
    @PostMapping()
    @RequiresPermissions("system:user:add")
    @ResponseBody
    public R add(@Validated SysUserAddRequest sysUserAddRequest){
        int result =  sysUserService.insertUser(sysUserAddRequest);
        if(result > 0){
            return R.success("添加成功");
        }
        return R.success("添加失败");
    }

    @RequiresPermissions("system:user:query")
    @ApiOperation("获取用户详细信息")
    @GetMapping("/{userId}")
    @ResponseBody
    public R getInfo(@PathVariable(value="userId") Long userId){
        SysUserPo sysUserPo = sysUserService.getUserInfo( userId);
        SysUserVo sysUserVo =  sysUserConvert.sysUserVo(sysUserPo);
        return R.success(sysUserVo);
    }

    @ApiOperation("修改用户")
    @PutMapping("/{userId}")
    @RequiresPermissions("system:user:edit")
    @ResponseBody
    public R edit(@PathVariable(value="userId") Long userId,@Validated SysUserEditRequest sysUserEditRequest){
        int result =  sysUserService.updateUser(userId,sysUserEditRequest);
        if(result > 0){
            return R.success("修改成功");
        }
        return R.success("修改失败");
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{userId}")
    @RequiresPermissions("system:user:removed")
    @ResponseBody
    public R delete(@PathVariable(value = "userId") Long userId){
        sysUserService.deleteUser(userId);
        return R.success("删除成功");
    }


    @ApiOperation("设置账号状态")
    @PatchMapping("/{userId}/status/{status}")
    @RequiresPermissions("system:user:edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "帐号状态（1正常 0停用）", dataType = "String")
    })
    public R setStatus(@PathVariable("userId") Long userId,@PathVariable("status") String status){
        sysUserService.setStatus(userId,status);
        return R.success("修改成功");
    }





}
