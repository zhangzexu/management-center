package com.ym52n.web;

import com.ym52n.comm.aop.LoggerManage;
import com.ym52n.domain.SysPermission;
import com.ym52n.domain.SysRole;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponseData;
import com.ym52n.domain.result.ResponsePageData;
import com.ym52n.service.SysPermissionService;
import com.ym52n.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PermissionController extends BaseController{


    @Autowired
    SysPermissionService sysPermissionService;
    @RequestMapping(Const.PAGE_USER+"permissionList.json")
    public ResponsePageData roleList(HttpServletRequest request) throws Exception{
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String name = request.getParameter("name");
        String available = request.getParameter("available");
        String resourceType = request.getParameter("resourceType");
        String permission = request.getParameter("permission");
        SysPermission sysPermission = new SysPermission();
        sysPermission.setName(name);
        sysPermission.setResourceType(resourceType);
        sysPermission.setPermission(permission);
        if("true".equals(available)){
            sysPermission.setAvailable(true);
        }else if("false".equals(available)) sysPermission.setAvailable(false);
        else sysPermission.setAvailable(null);

        Page<SysPermission> sysPermissionPage = sysPermissionService.findAll(sysPermission,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,sysPermissionPage.getContent(),sysPermissionPage.getTotalElements());
    }
    @LoggerManage(description = "权限添加")
    @PostMapping(Const.PAGE_USER+"permissionAdd.json")
    public Response roleAdd(@RequestBody SysPermission sysPermission) throws Exception{

        if(sysPermissionService.save(sysPermission)){
            return new Response(ExceptionMsg.SUCCESS);
        }
        return new Response(ExceptionMsg.FAILED);
    }
    @LoggerManage(description = "父级菜单根据名称查询ID")
    @PostMapping(Const.PAGE_USER+"permissionSelectParentId.json")
    public Response permissionParentId(HttpServletRequest request) {
        String name = request.getParameter("name");
        SysPermission sysPermission = sysPermissionService.findByName(name);
        if(sysPermission==null)
            return new Response(ExceptionMsg.FAILED);

        return new ResponseData(ExceptionMsg.SUCCESS,sysPermission.getId());
    }

    @LoggerManage(description = "父级菜单根据ID查询名称")
    @PostMapping(Const.PAGE_USER+"permissionSelectParentName.json")
    public Response permissionParentName(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysPermission sysPermission = sysPermissionService.findById(Long.parseLong(id));
        if(sysPermission==null)
            return new Response(ExceptionMsg.FAILED);
        return new ResponseData(ExceptionMsg.SUCCESS,sysPermission.getId());
    }

    @LoggerManage(description = "删除权限设置")
    @PostMapping(Const.PAGE_USER+"permissionDelete.json")
    public Response roleDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");

        if(!sysPermissionService.deleteId(uids))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }


    @LoggerManage(description = "更新权限设置")
    @PostMapping(Const.PAGE_USER+"permissionUpdate.json")
    public Response roleUpdate(@RequestBody SysPermission sysPermission) {
       SysPermission oldPermission = sysPermissionService.findById(sysPermission.getId());
       sysPermission.setCreateBy(oldPermission.getCreateBy());
       sysPermission.setCreateDate(oldPermission.getCreateDate());
       sysPermission.setLastModifiedBy(oldPermission.getLastModifiedBy());
       sysPermission.setLastModifiedDate(oldPermission.getLastModifiedDate());
        if(!sysPermissionService.update(sysPermission))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }



}
