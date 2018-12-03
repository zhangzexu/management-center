package com.ym52n.service;

import com.ym52n.domain.Crops;
import com.ym52n.domain.PlantingArea;
import org.springframework.data.domain.Page;

public interface PlantingAreaService {
    public PlantingArea findByUid(String uid);
    public Boolean save(PlantingArea plantingArea);
    public Boolean deleteUid(String[] uid);
    public Boolean update(PlantingArea plantingArea);
    public Page<PlantingArea> findAll(PlantingArea plantingArea, Integer page, Integer limit);
}
