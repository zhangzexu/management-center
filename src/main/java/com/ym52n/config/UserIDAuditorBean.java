package com.ym52n.config;

import com.ym52n.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;


import java.util.Optional;
@Configuration
public class UserIDAuditorBean implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        SysUser sysUser = null;
        //SysUser sysUser =(SysUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser==null){
            return Optional.of("未登录用户");
        }else {
            return Optional.of(sysUser.getUserName());
        }

    }
}
