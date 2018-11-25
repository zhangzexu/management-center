package com.ym52n.service.impl;

import com.ym52n.domain.SysUser;
import com.ym52n.repository.SysUserDao;
import com.ym52n.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    SysUserDao sysUserDao;
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserDao.findByUserName(userName);
    }
}
