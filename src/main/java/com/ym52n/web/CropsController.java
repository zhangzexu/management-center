package com.ym52n.web;

import com.ym52n.domain.Crops;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponsePageData;
import com.ym52n.service.CropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title
 * @ClassName CropsController
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/2 17:31
 * @Version V1.0
 */
public class CropsController extends BaseController {

    public static void main(String[] args) {
        System.out.println(new Integer("-1"));
    }

    @Autowired
    CropsService cropsService;
    @RequestMapping(Const.PAGE_PRODUCTS+"getCropsList.json")
    public ResponsePageData getCropsList(HttpServletRequest request) throws Exception{
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String nameCn = request.getParameter("nameCn");
        String nameEn = request.getParameter("nameEn");
        Integer isOrganic = new Integer(request.getParameter("isOrganic"));
        String cropsType = request.getParameter("cropsType");
        Integer available = new Integer(request.getParameter("available"));
        Crops crops = new Crops();
        crops.setAvailable(available);
        crops.setCropsType(cropsType);
        crops.setIsOrganic(isOrganic);
        crops.setNameCn(nameCn);
        crops.setNameEn(nameEn);
        Page<Crops> cropsPage = cropsService.findAll(crops,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,cropsPage.getContent(),cropsPage.getTotalElements());
    }
    @PostMapping(Const.SIMULATION_ENVIRONMENT+"cropsDelete.json")
    public Response environmentDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");
        if(!cropsService.deleteUid(uids))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }

    @PostMapping(Const.SIMULATION_ENVIRONMENT+"cropsUpdate.json")
    public Response environmentUpdate(@RequestBody Crops crops) {
        Crops oldCrops = cropsService.findByUid(crops.getUid());
        crops.setCreateBy(oldCrops.getCreateBy());
        crops.setCreateDate(oldCrops.getCreateDate());
        crops.setLastModifiedBy(oldCrops.getLastModifiedBy());
        crops.setLastModifiedDate(oldCrops.getLastModifiedDate());
        if(!cropsService.update(crops))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }
}
