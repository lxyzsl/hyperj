package com.hyperj.system.service.impl;

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

    @Override
    public List<SysUserVo> getUserList(SysUserListRequest sysUserListRequest) {
       List<SysUserPo> list =  sysUserDao.selectUserList(sysUserListRequest);
       return sysUserConvert.sysUserVoList(list);
    }

    @Override
    public int insertUser(SysUserAddRequest sysUserAddRequest) {
        System.out.println(this.checkUserNameUnique(sysUserAddRequest.getUserName()));
//        if(this.checkUserNameUnique(sysUserAddRequest.getUserName())>=1){
//
//        }
        return 0;
    }

    @Override
    public int checkUserNameUnique(String userName) {
        return sysUserDao.checkUserNameUnique(userName);
    }
}
