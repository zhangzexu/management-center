package com.ym52n.schedule;

import com.ym52n.domain.Environment;
import com.ym52n.service.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;


@Configuration
@EnableScheduling
public class CleraRedis {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    EnvironmentService environmentService;
    @Scheduled(initialDelay=1000, fixedDelay=60*1000*30)
    public void cleraRedis(){
        log.info("redis clera 开始------");
        List<String> list = new Vector<>();
        list.add("temperature");
        list.add("dampness");
        list.add("lx");
        list.add("tuTemperature");
        list.add("water");
        list.add("ppm");
        Environment environment = null;
        Map<String,String> temperatures =redisTemplate.opsForHash().entries("temperature");
        Map<String,String> dampnesss =redisTemplate.opsForHash().entries("dampness");
        Map<String,String> lxs =redisTemplate.opsForHash().entries("lx");
        Map<String,String> tuTemperatures =redisTemplate.opsForHash().entries("tuTemperature");
        Map<String,String> waters =redisTemplate.opsForHash().entries("water");
        Map<String,String> ppms =redisTemplate.opsForHash().entries("ppm");
        Set<String> sets = new TreeSet<>();
        sets.addAll(temperatures.keySet());
        sets.addAll(dampnesss.keySet());
        sets.addAll(lxs.keySet());
        sets.addAll(tuTemperatures.keySet());
        sets.addAll(waters.keySet());
        sets.addAll(ppms.keySet());
        log.info("redis cler 入库 开始------");
        for (String key: sets) {
            environment = new Environment();
            environment.setDate(key);
            environment.setTemperature(temperatures.get(key));
            environment.setDampness(dampnesss.get(key));
            environment.setLx(lxs.get(key));
            environment.setTuTemperature(tuTemperatures.get(key));
            environment.setWater(waters.get(key));
            environment.setPpm(ppms.get(key));
            environmentService.save(environment);

        }
        log.info("redis cler 入库 结束------");
        redisTemplate.delete(list);
        log.info("redis clera 结束--------");
    }


}
