package com.ym52n.service.impl;

import com.ym52n.domain.SysPermission;
import com.ym52n.domain.SysRole;
import com.ym52n.repository.SysPermissionDao;
import com.ym52n.repository.SysRoleDao;
import com.ym52n.service.SysPermissionService;
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
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    SysPermissionDao sysPermissionDao;


    @Override
    public Boolean save(SysPermission sysPermission) {
        if(sysPermissionDao.save(sysPermission)!=null)
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
    public Boolean deleteId(String[] id) {
        for(int i = 0 ;i<id.length;i++){
            sysPermissionDao.deleteById(Long.parseLong(id[i]));
            sysPermissionDao.deleteByParentId(Long.parseLong(id[i]));
            if(sysPermissionDao.existsById(Long.parseLong(id[i]))){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean update(SysPermission sysPermission) {
        if(sysPermissionDao.saveAndFlush(sysPermission)==null)
            return false;
        return true;
    }


    @Override
    public SysPermission findById(Long id) {
        return sysPermissionDao.findById(id).get();
    }

    @Override
    public SysPermission findByName(String name) {
        return sysPermissionDao.findByName(name);
    }

    @Override
    public Page<SysPermission> findAll(SysPermission sysPermission,Integer page,Integer limit) {
        Specification<SysPermission> specification = new Specification<SysPermission>() {
            @Override
            public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isBlank(sysPermission.getName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+sysPermission.getName()+"%"));
                }
                if(!StringUtils.isBlank(sysPermission.getResourceType())){
                    predicates.add(criteriaBuilder.like(root.get("resourceType").as(String.class),"%"+sysPermission.getResourceType()+"%"));
                }
                if(sysPermission.getAvailable()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),sysPermission.getAvailable()));
                }
                if(!StringUtils.isBlank(sysPermission.getPermission())){
                    predicates.add(criteriaBuilder.like(root.get("permission").as(String.class),"%"+sysPermission.getPermission()+"%"));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(page,limit,sort);
        return sysPermissionDao.findAll(specification,pageable);

    }
}
