package com.ym52n.config;

import com.ym52n.config.listener.MyShrioSessionListener;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, MyFormAuthenticationFilter authcFilter,
                                             JCaptchaValidateFilter jCaptchaValidateFilter				 ) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc",authcFilter);
		filters.put("jCaptchaValidate",jCaptchaValidateFilter);
//		filters.put("kickout",kickoutSessionControlFilter());
		shiroFilterFactoryBean.setFilters(filters);
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/captcha*", "anon");
		filterChainDefinitionMap.put("/json/**", "anon");
		filterChainDefinitionMap.put("/layui/**", "anon");
		filterChainDefinitionMap.put("/layui-v2-4-5/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		//静态资源
		filterChainDefinitionMap.put("/page/login/login.js", "anon");
		filterChainDefinitionMap.put("/page/login/login.html", "anon");
		filterChainDefinitionMap.put("/page/user/**", "anon");
		filterChainDefinitionMap.put("/**", "anon");

		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/page/login/login.json","jCaptchaValidate,authc");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//		filterChainDefinitionMap.put("/**", "user");

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/page/login/login.json");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/log/login.json");

		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}


	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(sessionManager());

		return securityManager;
	}
	@Bean
	public SessionManager sessionManager(){
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setSessionDAO(sessionDAO());
		defaultWebSessionManager.setSessionValidationInterval(1000*60*10);
		defaultWebSessionManager.setGlobalSessionTimeout(1000*60*10);
		List<SessionListener> sessionListeners = new Vector<>();
		//添加Session会话器
		sessionListeners.add(new MyShrioSessionListener());
		defaultWebSessionManager.setSessionListeners(sessionListeners);
		return defaultWebSessionManager;
	}
	@Bean
	public SessionDAO sessionDAO(){
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		enterpriseCacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		return enterpriseCacheSessionDAO;
	}


	@Bean
	public SimpleCookie rememberMeCookie(){
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//<!-- 记住我cookie生效时间7天 ,单位秒;-->
		simpleCookie.setMaxAge(7*24*60*60);
		simpleCookie.setHttpOnly(true);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
	 * @return rememberMeManager
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}


	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

//	@Bean(name="simpleMappingExceptionResolver")
//	public SimpleMappingExceptionResolver
//	createSimpleMappingExceptionResolver() {
//		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//		Properties mappings = new Properties();
//		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//		mappings.setProperty("UnauthorizedException","403");
//		r.setExceptionMappings(mappings);  // None by default
//		r.setDefaultErrorView("error");    // No default
//		r.setExceptionAttribute("ex");     // Default is "exception"
//		//r.setWarnLogCategory("example.MvcLogger");     // No default
//		return r;
//	}
//	<property name="usernameParam" value="username"/>
//    <property name="passwordParam" value="password"/>
//    <property name="rememberMeParam" value="rememberMe"/>
//    <property name="failureKeyAttribute" value="shiroLoginFailure"/>shiroLoginFailure
	@Bean(name="authcFilter")
	public MyFormAuthenticationFilter getMyFormAuthenticationFilter(){
		MyFormAuthenticationFilter myFormAuthenticationFilter = new MyFormAuthenticationFilter();
		myFormAuthenticationFilter.setFailureKeyAttribute("shiroLoginFailure");
		myFormAuthenticationFilter.setPasswordParam("password");
		myFormAuthenticationFilter.setUsernameParam("username");
		myFormAuthenticationFilter.setRememberMeParam("rememberMe");
		return myFormAuthenticationFilter;
	}
//	 <property name="jcaptchaEbabled" value="true"/>
//    <property name="jcaptchaParam" value="jcaptchaCode"/>
//    <property name="failureKeyAttribute" value="shiroLoginFailure"/>
	@Bean(name="jCaptchaValidateFilter")
	public JCaptchaValidateFilter getJCaptchaValidateFilter(){
		JCaptchaValidateFilter jCaptchaValidateFilter = new JCaptchaValidateFilter();
		jCaptchaValidateFilter.setFailureKeyAttribute("shiroLoginFailure");
		jCaptchaValidateFilter.setJcaptchaEbabled(true);
		jCaptchaValidateFilter.setJcaptchaParam("jcaptchaCode");

		return jCaptchaValidateFilter;

	}


}