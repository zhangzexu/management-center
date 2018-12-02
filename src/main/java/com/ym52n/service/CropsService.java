package com.ym52n.service;

import com.ym52n.domain.Crops;
import com.ym52n.domain.SoilMoisture;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface CropsService {
    public Crops findByUid(String uid);

    public Boolean save(Crops crops);
    public Boolean deleteUid(String[] uid);
    public Boolean update(Crops crops);
    public Page<Crops> findAll(Crops crops, Integer page, Integer limit);
}
