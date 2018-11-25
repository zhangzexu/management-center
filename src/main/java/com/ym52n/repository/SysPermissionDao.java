package com.ym52n.repository;

import com.ym52n.domain.SysPermission;
import com.ym52n.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SysPermissionDao extends JpaRepository<SysPermission,Long> , JpaSpecificationExecutor<SysPermission> {
     public SysPermission findByName(String name);

     public Long deleteByParentId(Long parentId);
}
