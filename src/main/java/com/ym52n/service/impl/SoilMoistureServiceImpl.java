package com.ym52n.service.impl;

import com.ym52n.domain.SoilMoisture;
import com.ym52n.repository.SoilMoistureDao;
import com.ym52n.service.SoilMoistureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class SoilMoistureServiceImpl implements SoilMoistureService {
    @Resource
    SoilMoistureDao soilMoistureDao;
    @Override
    public Boolean save(SoilMoisture soilMoisture) {
        if(soilMoistureDao.save(soilMoisture)!=null)
            return true;
        return false;

    }
    /**
     * 删除一个权限,根据ID删除所有的子权限
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteUid(String[] uid) {
        for(int i = 0 ;i<uid.length;i++){
            soilMoistureDao.deleteById(uid[i]);
            if(soilMoistureDao.existsById(uid[i])){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(SoilMoisture environment) {
        if(soilMoistureDao.saveAndFlush(environment)==null)
            return false;
        return true;
    }
     @Override
    public SoilMoisture findByUid(String uid) {
        return soilMoistureDao.findById(uid).get();
    }

    @Override
    public SoilMoisture findByDate(String date) {
        return soilMoistureDao.findByDate(date);
    }

    @Override
    public Page<SoilMoisture> findAll(SoilMoisture soilMoisture, Integer page, Integer limit) {
        Specification<SoilMoisture> specification = new Specification<SoilMoisture>() {
            @Override
            public Predicate toPredicate(Root<SoilMoisture> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                String[] date = soilMoisture.getDate().split(",");
                if(date.length==2&&!StringUtils.isBlank(date[0])&&!StringUtils.isBlank(date[1])){
                    predicates.add(criteriaBuilder.between(root.get("date").as(String.class),date[0],date[1]));
                }

                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable = new PageRequest(page,limit,sort);
        return soilMoistureDao.findAll(specification,pageable);

    }

}
