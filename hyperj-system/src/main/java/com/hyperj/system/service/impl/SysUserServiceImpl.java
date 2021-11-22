package com.hyperj.system.service.impl;

import com.hyperj.common.utils.ShiroUtils;
import com.hyperj.framework.web.utils.R;
import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.convert.SysUserConvert;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.dao.SysUserDao;
import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.vo.SysUserVo;
import com.hyperj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserConvert sysUserConvert;

    /**
     * 获取用户列表
     * @param sysUserListRequest
     * @return
     */
    @Override
    public List<SysUserPo> getUserList(SysUserListRequest sysUserListRequest) {
//        List<SysUserPo> list =  sysUserDao.selectUserList(sysUserListRequest);
//        return sysUserConvert.sysUserVoList(list);
        return  sysUserDao.selectUserList(sysUserListRequest);
    }

    /**
     * 新增用户
     * @param sysUserAddRequest
     * @return
     */
    @Override
    public R insertUser(SysUserAddRequest sysUserAddRequest) {
        if(this.checkUserNameUnique(sysUserAddRequest.getUserName())>=1){
            return R.error("账号已存在");
        }
        if(this.checkMobileUnique(sysUserAddRequest.getMobile())>=1){
            return R.error("手机号已存在");
        }
        if(sysUserAddRequest.getNickName() != null && (this.checkNickNameUnique(sysUserAddRequest.getNickName())>=1)){
            return R.error("昵称已存在");
        }
        if(sysUserAddRequest.getEmail() != null && (this.checkEmailUnique(sysUserAddRequest.getEmail())>=1)){
            return R.error("游戏已存在");
        }

        String salt = ShiroUtils.randomSalt();

        sysUserAddRequest.setPassword(
                ShiroUtils.encryptPassword(
                        sysUserAddRequest.getUserName(),sysUserAddRequest.getPassword(),salt
                )
        );

        SysUserPo sysUserPo = sysUserConvert.sysUserRequestToPo(sysUserAddRequest);

        sysUserPo.setSalt(salt);
        //TODO:通过shiro获取当前登录用户的userName并写入到sysUserAddRequest中

        int result = sysUserDao.insertUser(sysUserPo);
        if(result > 0){
            return R.success("添加成功");
        }
        return R.success("添加失败");
    }


    /**
     * 校验账号是否唯一
     * @param userName
     * @return
     */
    @Override
    public int checkUserNameUnique(String userName) {
        return sysUserDao.checkUserNameUnique(userName);
    }

    /**
     * 校验昵称是否唯一
     * @param nickName
     * @return
     */
    @Override
    public int checkNickNameUnique(String nickName) {
        return sysUserDao.checkNickNameUnique(nickName);
    }

    /**
     * 校验手机号是否唯一
     * @param mobile
     * @return
     */
    @Override
    public int checkMobileUnique(String mobile) {
        return sysUserDao.checkMobileUnique(mobile);
    }

    /**
     * 校验邮箱是否唯一
     * @param email
     * @return
     */
    @Override
    public int checkEmailUnique(String email) {
        return sysUserDao.checkEmailUnique(email);
    }


}
