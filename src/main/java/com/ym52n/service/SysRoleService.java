package com.ym52n.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ym52n.domain.SysRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRoleService {


    public SysRole findByUid(String uid);
    public Boolean save(SysRole sysRole);
    public Boolean deleteUid(String[] uids);
    public Boolean update(SysRole sysRole);
    public Page<SysRole> findAll(SysRole sysRole,Integer page,Integer limit);
}
