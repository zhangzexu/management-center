package com.ym52n.repository;

import com.ym52n.domain.Crops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Title
 * @ClassName CropsDao
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/2 17:02
 * @Version V1.0
 */
public interface CropsDao extends JpaRepository<Crops,String> , JpaSpecificationExecutor<Crops> {

}
