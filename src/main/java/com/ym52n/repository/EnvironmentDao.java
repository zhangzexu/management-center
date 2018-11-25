package com.ym52n.repository;

import com.ym52n.domain.Environment;
import com.ym52n.domain.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface EnvironmentDao extends JpaRepository<Environment,String>, JpaSpecificationExecutor<Environment> {
    public Environment findByDate(String date);
    public Environment findByUid(String uid);

}