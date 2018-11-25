package com.ym52n.web;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ym52n.domain.result.ExceptionMsg;
import com.ym52n.domain.result.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
@RestController
public class LoginController {

    @Autowired
    SessionDAO sessionDAO;

    /**
     * 登录验证接口
     * @param request
     * @param map
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(Const.PAGE_LOGIN+"login.json")
    public Response login(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response) throws Exception{
        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "1111";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");

                return new Response(ExceptionMsg.UnknownAccount);
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                return new Response(ExceptionMsg.LoginNameOrPassWordError);
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
                return new Response(ExceptionMsg.JcaptchaCodeError);
            } else {
                msg = "未知错误 >> "+exception;
                System.out.println("else -- >" + exception);
                return new Response(ExceptionMsg.NOKnowError);
            }
        }

        return new Response(ExceptionMsg.SUCCESS);
    }

    @GetMapping(Const.PAGE_LOGIN+"login.json")
    public JSONObject login(HttpServletResponse response) throws Exception{
        response.sendRedirect("/page/login/login.html");
        return null;
    }

    //登录成功后返回的json数据
    @RequestMapping(Const.LOG+"login.json")
    public Response kickout(){

        return new Response(ExceptionMsg.SUCCESS);
    }





}