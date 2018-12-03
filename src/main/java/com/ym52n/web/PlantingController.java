package com.ym52n.web;


import com.ym52n.domain.PlantingArea;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponsePageData;

import com.ym52n.service.PlantingAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title
 * @ClassName PlantingController
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/3 22:24
 * @Version V1.0
 */
@RestController
public class PlantingController extends BaseController{
    @Autowired
    PlantingAreaService plantingAreaService;

    @RequestMapping(Const.PAGE_PRODUCTS+"plantingAreaList.json")
    public ResponsePageData cropsList(HttpServletRequest request){
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String name = request.getParameter("name");
        String areaType = request.getParameter("areaType");
        String irrigationType = request.getParameter("irrigationType");
        String available = request.getParameter("available");
        PlantingArea plantingArea = new PlantingArea();
        plantingArea.setAvailable(available);
        plantingArea.setAreaType(areaType);
        plantingArea.setIrrigationType(irrigationType);
        plantingArea.setName(name);
        logger.info(plantingArea.toString());
        Page<PlantingArea> plantingAreaPage = plantingAreaService.findAll(plantingArea,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,plantingAreaPage.getContent(),plantingAreaPage.getTotalElements());
    }

    @PostMapping(Const.PAGE_PRODUCTS+"plantingAreaAdd.json")
    public Response cropsAdd(@RequestBody PlantingArea plantingArea){
        if(plantingAreaService.save(plantingArea)){
            return new Response(ExceptionMsg.SUCCESS);
        }
        return new Response(ExceptionMsg.FAILED);
    }

    @PostMapping(Const.PAGE_PRODUCTS+"plantingAreaDelete.json")
    public Response environmentDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");
        if(!plantingAreaService.deleteUid(uids))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }

    @PostMapping(Const.PAGE_PRODUCTS+"plantingAreaUpdate.json")
    public Response environmentUpdate(@RequestBody PlantingArea plantingArea) {
        PlantingArea oldPlantingArea = plantingAreaService.findByUid(plantingArea.getUid());
        plantingArea.setCreateBy(oldPlantingArea.getCreateBy());
        plantingArea.setCreateDate(oldPlantingArea.getCreateDate());

        if(!plantingAreaService.update(plantingArea))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }
}
