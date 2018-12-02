package com.ym52n.service.impl;

import com.ym52n.domain.Crops;
import com.ym52n.repository.CropsDao;
import com.ym52n.service.CropsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @ClassName CropsServiceImpl
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/2 17:07
 * @Version V1.0
 */
public class CropsServiceImpl implements CropsService {

    @Autowired
    CropsDao cropsDao;
    @Override
    public Crops findByUid(String uid) {
        return cropsDao.findById(uid).get();
    }

    @Override
    public Boolean save(Crops crops) {
        if(cropsDao.save(crops)!=null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUid(String[] uid) {
        for(int i = 0 ;i<uid.length;i++){
            cropsDao.deleteById(uid[i]);
            if(cropsDao.existsById(uid[i])){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(Crops crops) {
        if(cropsDao.saveAndFlush(crops)==null)
            return false;
        return true;
    }

    @Override
    public Page<Crops> findAll(Crops crops, Integer page, Integer limit) {
        Specification<Crops> specification = new Specification<Crops>() {
            @Override
            public Predicate toPredicate(Root<Crops> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isBlank(crops.getNameCn())){
                    predicates.add(criteriaBuilder.like(root.get("nameCn").as(String.class),"%"+crops.getNameCn()+"%"));
                }
                if(!StringUtils.isBlank(crops.getNameEn())){
                    predicates.add(criteriaBuilder.like(root.get("nameEn").as(String.class),"%"+crops.getNameEn()+"%"));
                }
                if(crops.getIsOrganic()!=-1){
                    predicates.add(criteriaBuilder.equal(root.get("isOrganic").as(Integer.class),crops.getIsOrganic()));
                }
                if(StringUtils.isBlank(crops.getCropsType())){
                    predicates.add(criteriaBuilder.equal(root.get("cropsType").as(String.class),crops.getCropsType()));
                }
                if(crops.getAvailable()!=-1){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),crops.getAvailable()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        Pageable pageable = new PageRequest(page,limit,sort);
        return cropsDao.findAll(specification,pageable);
    }
}
