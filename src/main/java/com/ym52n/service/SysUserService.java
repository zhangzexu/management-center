package com.ym52n.service;

import com.ym52n.domain.SysUser;

public interface SysUserService {
    public SysUser findByUserName(String userName);
}
