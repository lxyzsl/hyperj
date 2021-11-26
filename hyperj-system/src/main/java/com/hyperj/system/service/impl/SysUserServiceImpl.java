package com.hyperj.system.service.impl;

import com.hyperj.common.exception.CustomException;
import com.hyperj.common.utils.ShiroUtils;
import com.hyperj.system.bean.request.SysUserAddRequest;
import com.hyperj.system.bean.request.SysUserEditRequest;
import com.hyperj.system.convert.SysUserConvert;
import com.hyperj.system.bean.request.SysUserListRequest;
import com.hyperj.system.dao.SysUserDao;
import com.hyperj.system.bean.po.SysUserPo;
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
    public int insertUser(SysUserAddRequest sysUserAddRequest) {
        if(this.checkUserNameUnique(sysUserAddRequest.getUserName())>=1){
            throw new CustomException("账号已存在");
        }
        if(this.checkMobileUnique(sysUserAddRequest.getMobile())>=1){
            throw new CustomException("手机号已存在");
        }
        if(sysUserAddRequest.getNickName() != null && (this.checkNickNameUnique(sysUserAddRequest.getNickName())>=1)){
            throw new CustomException("昵称已存在");
        }
        if(sysUserAddRequest.getEmail() != null && (this.checkEmailUnique(sysUserAddRequest.getEmail())>=1)){
            throw new CustomException("邮箱已存在");
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

        return sysUserDao.insertUser(sysUserPo);
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


    /**
     * 获取用户信息
     */
    @Override
    public SysUserPo getUserInfo(Long userId){
        return sysUserDao.getUserInfo(userId);
    }

    /**
     * 修改用户信息
     */
    @Override
    public int updateUser(Long userId,SysUserEditRequest sysUserEditRequest) {


        SysUserPo sysUserPo = this.getUserInfo(userId);

        if(sysUserPo.getUserId() == null){
            throw new CustomException("该用户不存在");
        }
//
//        if(this.checkMobileUnique(sysUserEditRequest.getMobile())>=1){
//            throw new CustomException("手机号已存在");
//        }
//        if(sysUserEditRequest.getNickName() != null && (this.checkNickNameUnique(sysUserEditRequest.getNickName())>=1)){
//            throw new CustomException("昵称已存在");
//
//        }
//        if(sysUserEditRequest.getEmail() != null && (this.checkEmailUnique(sysUserEditRequest.getEmail())>=1)){
//            throw new CustomException("邮箱已存在");
//
//        }
        SysUserPo sysUserEdit = sysUserConvert.sysUserEditRequestToPo(sysUserEditRequest);
        if(sysUserEdit.getPassword() != null){
            String salt = ShiroUtils.randomSalt();
            sysUserEdit.setPassword(
                    ShiroUtils.encryptPassword(
                            sysUserPo.getUserName(),sysUserEdit.getPassword(),salt
                    )
            );
            sysUserEdit.setSalt(salt);
        }
        sysUserEdit.setUserId(userId);
        return sysUserDao.updateUser(sysUserEdit);
    }

    /**
     * 删除用户（假删除）
     */
    @Override
    public void deleteUser(Long userId) {
         sysUserDao.deleteUser(userId);
    }

    /**
     * 设置账号状态
     */
    @Override
    public void setStatus(Long userId, String status) {
        sysUserDao.setStatus(userId,status);
    }

    /**
     * 根据用户名获取用户信息
     */
    @Override
    public SysUserPo getUserByUserName(String userName) {
        return sysUserDao.getUserByUserName(userName);
    }


}
