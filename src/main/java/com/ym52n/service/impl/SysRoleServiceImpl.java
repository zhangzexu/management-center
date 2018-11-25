package com.ym52n.service.impl;

import com.ym52n.domain.SysRole;
import com.ym52n.repository.SysRoleDao;
import com.ym52n.service.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    SysRoleDao sysRoleDao;


    @Override
    public Boolean save(SysRole sysRole) {
        if(sysRoleDao.save(sysRole)!=null)
            return true;
        return false;

    }

    @Override
    @Transactional
    public Boolean deleteUid(String[] uids) {
        for(int i = 0 ;i<uids.length;i++){
            sysRoleDao.deleteById(uids[i]);
            if(sysRoleDao.existsById(uids[i])){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(SysRole sysRole) {
        if(sysRoleDao.saveAndFlush(sysRole)==null)
            return false;
        return true;
    }


    @Override
    public SysRole findByUid(String uid) {
        return sysRoleDao.findById(uid).get();
    }

    @Override
    public Page<SysRole> findAll(SysRole sysRole,Integer page,Integer limit) {
        Specification<SysRole> specification = new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isBlank(sysRole.getRole())){
                    predicates.add(criteriaBuilder.like(root.get("role").as(String.class),"%"+sysRole.getRole()+"%"));
                }
                if(sysRole.getAvailable()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),sysRole.getAvailable()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.ASC,"uid");
        Pageable pageable = new PageRequest(page,limit,sort);
        return sysRoleDao.findAll(specification,pageable);

    }
}
