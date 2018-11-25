package com.ym52n.config;


import com.ym52n.config.shrioException.MoreAccountException;
import com.ym52n.domain.SysPermission;
import com.ym52n.domain.SysRole;
import com.ym52n.domain.SysUser;
import com.ym52n.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Collection;


public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SysUserService sysUserService;
    @Autowired
    private SessionDAO sessionDAO;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo  = (SysUser)principals.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }


    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println(token.getCredentials());
        SysUser userInfo = sysUserService.findByUserName(username);
        if(userInfo == null){
            throw new UnknownAccountException();
        }
        /**
         * 添加用户验证
         */
        if(userInfo.getState()==2){
            throw new LockedAccountException();
        }
        System.out.println("----->>userInfo="+userInfo);

        Collection<Session> sessions = sessionDAO.getActiveSessions();//获取当前已登录的用户session列表
        for(Session session:sessions){
            System.out.println("登录ip:"+session.getHost());
            System.out.println("登录用户"+session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            System.out.println("最后操作日期:"+session.getLastAccessTime());
            Subject s = new Subject.Builder().session(session).buildSubject();
            if (s.isAuthenticated()) {
                SysUser user = (SysUser) s.getPrincipal();
                if (user.getUid().equals(userInfo.getUid())) {
                    throw new MoreAccountException();
                }
            }
        }




        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName() //realm name
        );

        return authenticationInfo;
    }

}