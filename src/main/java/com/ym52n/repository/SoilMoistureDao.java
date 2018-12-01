package com.ym52n.repository;

import com.ym52n.domain.SoilMoisture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SoilMoistureDao extends JpaRepository<SoilMoisture, String>, JpaSpecificationExecutor<SoilMoisture> {
    public SoilMoisture findByDate(String date);

    public SoilMoisture findByUid(String uid);

}
