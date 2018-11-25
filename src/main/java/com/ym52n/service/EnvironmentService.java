package com.ym52n.service;

import com.ym52n.domain.Environment;
import org.springframework.data.domain.Page;

public interface EnvironmentService {
    public Environment findByUid(String uid);
    public Environment findByDate(String date);
    public Boolean save(Environment environment);
    public Boolean deleteUid(String[] uid);
    public Boolean update(Environment environment);
    public Page<Environment> findAll(Environment environment, Integer page, Integer limit);
}
