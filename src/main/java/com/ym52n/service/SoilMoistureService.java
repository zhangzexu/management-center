package com.ym52n.service;

import com.ym52n.domain.SoilMoisture;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SoilMoistureService {
    public SoilMoisture findByUid(String uid);
    public SoilMoisture findByDate(String date);
    public Boolean save(SoilMoisture soilMoisture);
    public Boolean deleteUid(String[] uid);
    public Boolean update(SoilMoisture soilMoisture);
    public Page<SoilMoisture> findAll(SoilMoisture soilMoisture, Integer page, Integer limit);
}
