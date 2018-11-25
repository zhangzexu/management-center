package com.ym52n.service.impl;

import com.ym52n.domain.Environment;
import com.ym52n.repository.EnvironmentDao;
import com.ym52n.service.EnvironmentService;
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
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {
    @Resource
    EnvironmentDao environmentDao;


    @Override
    public Boolean save(Environment environment) {
        if(environmentDao.save(environment)!=null)
            return true;
        return false;

    }

    /**
     * 删除一个权限,根据ID删除所有的子权限
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteUid(String[] uid) {
        for(int i = 0 ;i<uid.length;i++){
            environmentDao.deleteById(uid[i]);
            if(environmentDao.existsById(uid[i])){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(Environment environment) {
        if(environmentDao.saveAndFlush(environment)==null)
            return false;
        return true;
    }


    @Override
    public Environment findByUid(String uid) {
        return environmentDao.findById(uid).get();
    }

    @Override
    public Environment findByDate(String date) {
        return environmentDao.findByDate(date);
    }

    @Override
    public Page<Environment> findAll(Environment environment, Integer page, Integer limit) {
        Specification<Environment> specification = new Specification<Environment>() {
            @Override
            public Predicate toPredicate(Root<Environment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                String[] date = environment.getDate().split(",");
                if(date.length==2&&!StringUtils.isBlank(date[0])&&!StringUtils.isBlank(date[1])){
                    predicates.add(criteriaBuilder.between(root.get("date").as(String.class),date[0],date[1]));
                }

                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"date");
        Pageable pageable = new PageRequest(page,limit,sort);
        return environmentDao.findAll(specification,pageable);

    }
}
