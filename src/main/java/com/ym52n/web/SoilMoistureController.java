package com.ym52n.web;


import com.alibaba.fastjson.JSONObject;
import com.ym52n.domain.SoilMoisture;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponseData;
import com.ym52n.domain.result.ResponsePageData;
import com.ym52n.service.SoilMoistureService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class SoilMoistureController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SoilMoistureService soilMoistureService;
    @RequestMapping(Const.SIMULATION_ENVIRONMENT+"getSoilMoistureList.json")
    public ResponsePageData getSoilMoistureList(HttpServletRequest request) throws Exception{
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        SoilMoisture soilMoisture = new SoilMoisture();
        soilMoisture.setDate(startDate+","+endDate);
        Page<SoilMoisture> soilMoistures = soilMoistureService.findAll(soilMoisture,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,soilMoisture.getSoilSaturatedWaterContent(),soilMoistures.getTotalElements());
    }
    @PostMapping(Const.SIMULATION_ENVIRONMENT+"soilMoistureDelete.json")
    public Response soilMoistureDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");
        if(!soilMoistureService.deleteUid(uids))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }

    @PostMapping(Const.SIMULATION_ENVIRONMENT+"soilMoistureUpdate.json")
    public com.ym52n.domain.result.Response soilMoistureUpdate(@RequestBody SoilMoisture soilMoisture) {
        SoilMoisture oldSoilMoisture = soilMoistureService.findByUid(soilMoisture.getUid());
        soilMoisture.setCreateBy(oldSoilMoisture.getCreateBy());
        soilMoisture.setCreateDate(oldSoilMoisture.getCreateDate());
        soilMoisture.setLastModifiedBy(oldSoilMoisture.getLastModifiedBy());
        soilMoisture.setLastModifiedDate(oldSoilMoisture.getLastModifiedDate());
        if(!soilMoistureService.update(soilMoisture))
            return new Response(ExceptionMsg.FAILED);

        return new Response(ExceptionMsg.SUCCESS);
    }
}
