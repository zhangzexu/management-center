package com.ym52n.repository;

import com.ym52n.domain.SysUser;
import org.springframework.data.repository.CrudRepository;

public interface SysUserDao extends CrudRepository<SysUser,String> {
    /**通过username查找用户信息;*/
    public SysUser findByUserName(String userName);

}