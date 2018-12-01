package com.ym52n.web;

import com.alibaba.fastjson.JSONObject;
import com.ym52n.domain.Environment;

import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.domain.result.ResponseData;
import com.ym52n.domain.result.ResponsePageData;
import com.ym52n.service.EnvironmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


@RestController
public class EnvController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(Const.SIMULATION_ENVIRONMENT + "getKeyData.json")
    public Response mkaeData(String date,String key,Integer timeout) throws Exception{
        if(StringUtils.isBlank(key)){
            return new ResponseData(ExceptionMsg.FAILED);
        }
        String[] keys = key.split(",");
        TimeUnit.SECONDS.sleep(timeout);
        log.info(key);
        log.info(date);
        HashOperations hashOperations = redisTemplate.opsForHash();
        JSONObject jsonObject = new JSONObject();
        String value = null;
        try{
            for(int i = 0;i<keys.length;i++){
                value = hashOperations.get(keys[i],date).toString();
                jsonObject.put(keys[i],value);
            }
        }catch (Exception e){
            return new ResponseData(ExceptionMsg.FAILED);
        }
        log.info("获取到的数据为:{}", value);
        return new ResponseData(ExceptionMsg.SUCCESS, jsonObject);
    }
    @Autowired
    EnvironmentService environmentService;
    @RequestMapping(Const.SIMULATION_ENVIRONMENT+"getEnvironmentList.json")
    public ResponsePageData getEnvironmentList(HttpServletRequest request) throws Exception{
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"))-1;
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


        Environment environment = new Environment();
        environment.setDate(startDate+","+endDate);


        Page<Environment> environments = environmentService.findAll(environment,page,limit);

        return new ResponsePageData(ExceptionMsg.LayuiPageSuccess,environments.getContent(),environments.getTotalElements());
    }
    @PostMapping(Const.SIMULATION_ENVIRONMENT+"environmentDelete.json")
    public Response environmentDelete(HttpServletRequest request) {
        String[] uids = request.getParameter("dataValue").split(",");
        if(!environmentService.deleteUid(uids))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }

    @PostMapping(Const.SIMULATION_ENVIRONMENT+"environmentUpdate.json")
    public Response environmentUpdate(@RequestBody Environment environment) {
        Environment oldEnvironment = environmentService.findByUid(environment.getUid());
        environment.setCreateBy(oldEnvironment.getCreateBy());
        environment.setCreateDate(oldEnvironment.getCreateDate());
        environment.setLastModifiedBy(oldEnvironment.getLastModifiedBy());
        environment.setLastModifiedDate(oldEnvironment.getLastModifiedDate());
        if(!environmentService.update(environment))
            return new Response(ExceptionMsg.FAILED);
        return new Response(ExceptionMsg.SUCCESS);
    }

}
