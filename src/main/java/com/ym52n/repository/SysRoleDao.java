package com.ym52n.repository;

import com.ym52n.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SysRoleDao extends JpaRepository<SysRole,String> , JpaSpecificationExecutor<SysRole> {

}
