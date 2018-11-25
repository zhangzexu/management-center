package com.ym52n.config;

import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JCaptchaValidateFilter extends AccessControlFilter {
    private boolean jcaptchaEbabled = true;//是否开启验证码支持
    private String jcaptchaParam = "jcaptchaCode";//前台提交的验证码参数名
    private String failureKeyAttribute = "shiroLoginFailure"; //验证失败后存储到的属性名
    public void setJcaptchaEbabled(boolean jcaptchaEbabled) {
        this.jcaptchaEbabled = jcaptchaEbabled;
    }
    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }
    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }
    public String getCaptchaCode(ServletRequest request) {
        return WebUtils.getCleanParam(request, this.jcaptchaParam);
    }
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 从session获取正确的验证码
        Session session = SecurityUtils.getSubject().getSession();
        //页面输入的验证码
        String captchaCode = getCaptchaCode(request);
        String validateCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //判断验证码是否表单提交（允许访问）
        if ( !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }

        // 若验证码为空或匹配失败则返回false
        if(captchaCode == null) {
            return false;
        } else if (validateCode != null) {
            captchaCode = captchaCode.toLowerCase();
            validateCode = validateCode.toLowerCase();
            if(!captchaCode.equals(validateCode)) {
                return false;
            }
        }
        return true;
    }
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
        request.setAttribute(failureKeyAttribute, "kaptchaValidateFailed");
        return true;
    }
}
