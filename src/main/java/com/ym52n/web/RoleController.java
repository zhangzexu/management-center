package com.ym52n.web;

import com.ym52n.domain.SysRole;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponsePageData;
import com.ym52n.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
@RestController
public class RoleController extends BaseController{


    @Autowired
    SysRoleService sysRoleService;
    @RequestMapping(Const.PAGE_USER+"roleList.json")
    public ResponsePageData roleList(HttpServletRequest request) throws Exception{
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String role = request.getParameter("role");
        String available = request.getParameter("available");


        SysRole sysRole = new SysRole();
        sysRole.setRole(role);
        if("true".equals(available)){
           sysRole.setAvailable(true);
        }else if("false".equals(available)) sysRole.setAvailable(false);
        else sysRole.setAvailable(null);

        Page<SysRole> sysRolePage = sysRoleService.findAll(sysRole,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,sysRolePage.getContent(),sysRolePage.getTotalElements());
    }
    @PostMapping(Const.PAGE_USER+"roleAdd.json")
    public Response roleAdd(@RequestBody SysRole sysRole) throws Exception{

        if(sysRoleService.save(sysRole)){
            return new Response(ExceptionMsg.SUCCESS);
        }
        return new Response(ExceptionMsg.FAILED);
    }

    @PostMapping(Const.PAGE_USER+"roleDelete.json")
    public Response roleDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");
        if(!sysRoleService.deleteUid(uids))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }

    @PostMapping(Const.PAGE_USER+"roleUpdate.json")
    public Response roleUpdate(@RequestBody SysRole sysRole) {
       SysRole oldRole = sysRoleService.findByUid(sysRole.getUid());
       sysRole.setCreateBy(oldRole.getCreateBy());
       sysRole.setCreateDate(oldRole.getCreateDate());
       sysRole.setLastModifiedBy(oldRole.getLastModifiedBy());
       sysRole.setLastModifiedDate(oldRole.getLastModifiedDate());
        if(!sysRoleService.update(sysRole))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }



}
