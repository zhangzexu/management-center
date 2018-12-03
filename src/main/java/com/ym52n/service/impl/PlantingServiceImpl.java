package com.ym52n.service.impl;


import com.ym52n.domain.PlantingArea;
import com.ym52n.repository.impl.PlantingAreaDao;
import com.ym52n.service.PlantingAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @ClassName PlantingServiceImpl
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/3 22:17
 * @Version V1.0
 */
@Service
public class PlantingServiceImpl implements PlantingAreaService {
    @Autowired
    PlantingAreaDao plantingAreaDao;
    @Override
    public PlantingArea findByUid(String uid) {
        return plantingAreaDao.findById(uid).get();
    }

    @Override
    public Boolean save(PlantingArea plantingArea) {
        if(plantingAreaDao.save(plantingArea)==null)
            return false;
        return true;
    }

    @Override
    public Boolean deleteUid(String[] uid) {
        for(int i = 0 ;i<uid.length;i++){
            plantingAreaDao.deleteById(uid[i]);
            if(plantingAreaDao.existsById(uid[i])){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(PlantingArea plantingArea) {
        if(plantingAreaDao.saveAndFlush(plantingArea)==null)
            return false;
        return true;
    }

    @Override
    public Page<PlantingArea> findAll(PlantingArea plantingArea, Integer page, Integer limit) {

        Specification<PlantingArea> specification = new Specification<PlantingArea>() {
            @Override
            public Predicate toPredicate(Root<PlantingArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isBlank(plantingArea.getName())){
                    predicates.add(criteriaBuilder.like(root.get("available").as(String.class),"%"+plantingArea.getAvailable()+"%"));
                }
                if(!StringUtils.isBlank(plantingArea.getAreaType())){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(String.class),plantingArea.getAreaType()));
                }
                if(!StringUtils.isBlank(plantingArea.getIrrigationType())){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(String.class),plantingArea.getIrrigationType()));
                }


                if(!StringUtils.isBlank(plantingArea.getAvailable())){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(String.class),plantingArea.getAvailable()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        Pageable pageable = new PageRequest(page,limit,sort);
        return plantingAreaDao.findAll(specification,pageable);
    }
}
