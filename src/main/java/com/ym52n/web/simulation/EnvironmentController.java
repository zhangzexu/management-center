package com.ym52n.web.simulation;


import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import com.ym52n.web.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class EnvironmentController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate redisTemplate;
    //登录成功后返回的json数据
    @RequestMapping(Const.SIMULATION_ENVIRONMENT+"makeData.json")
    public Response mkaeData(Integer continuedTime,Integer sleep){
        continuedTime = continuedTime*60;
        DecimalFormat df = new DecimalFormat("#.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temperature =null;
        String dampness =null;
        String lx =null;
        String ppm =null;
        String tuTemperature =null;
        String water =null;
        while(continuedTime>0){
            Date d = new Date();
            temperature = df.format(Math.random()*40);
            dampness = df.format(40+Math.random()*40);
            lx = (int)(Math.random()*2000)+"";
            ppm = (int)(400+Math.random()*1000)+"";
            tuTemperature = df.format(8+Math.random()*25);
            water = df.format(15+Math.random()*40);
            continuedTime--;
            log.info("开始redis存入数据");
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put("temperature",sdf.format(d),temperature);
            log.info("temperature--{}--{}",sdf.format(d),temperature);
            hashOperations.put("dampness",sdf.format(d),dampness);
            log.info("dampness--{}--{}",sdf.format(d),dampness);
            hashOperations.put("lx",sdf.format(d),lx);
            log.info("lx--{}--{}",sdf.format(d),lx);
            hashOperations.put("ppm",sdf.format(d),ppm);
            log.info("ppm--{}--{}",sdf.format(d),ppm);
            hashOperations.put("tuTemperature",sdf.format(d),tuTemperature);
            log.info("tuTemperature--{}--{}",sdf.format(d),tuTemperature);
            hashOperations.put("water",sdf.format(d),water);
            log.info("water--{}--{}",sdf.format(d),water);
            log.info("结束redis存入数据");
            try{
                Thread.sleep(sleep*1000);
            }catch (Exception e){

            }


        }

        return new Response(ExceptionMsg.SUCCESS);
    }
}
