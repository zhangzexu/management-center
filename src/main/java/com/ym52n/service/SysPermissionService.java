package com.ym52n.service;

import com.ym52n.domain.SysPermission;
import org.springframework.data.domain.Page;

public interface SysPermissionService {


    public SysPermission findById(Long id);
    public SysPermission findByName(String name);
    public Boolean save(SysPermission sysPermission);
    public Boolean deleteId(String[] id);
    public Boolean update(SysPermission sysPermission);
    public Page<SysPermission> findAll(SysPermission sysPermission, Integer page, Integer limit);
}
