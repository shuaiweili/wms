package com.bq.wms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
@Slf4j
@RestController
public class LoginController {

    @GetMapping("userNoLogin.do")
    public Object noLogin() {
        JSONObject object = new JSONObject();
        object.put("message", "用户未登录");
        return object;
    }

    @GetMapping(value = "/login.do")
    public String login(String loginName, String password) {
        try {
            // 创建shiro需要的token
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginName, password.toCharArray());
            usernamePasswordToken.setRememberMe(true);// 记住

            try {
                SecurityUtils.getSubject().login(usernamePasswordToken);
            } catch (UnknownAccountException uae) {
                log.error("{用户} 验证未通过,未知账户", loginName);
                return "对用户[" + loginName + "]进行登录验证..验证未通过,未知账户";
            } catch (IncorrectCredentialsException ice) {
                log.error("用户{}..验证未通过,错误的凭证", loginName);
                ice.printStackTrace();
                return "对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证";
            } catch (LockedAccountException lae) {
                log.error("用户{}..验证未通过,账户已锁定", loginName);
                return "对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定";
            } catch (ExcessiveAttemptsException eae) {
                log.error("用户{}..验证未通过,错误次数过多", loginName);
                return "对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多";
            } catch (AuthenticationException ae) {
                // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                log.error("用户{}..验证未通过,堆栈轨迹如下", loginName, ae);
                return "用户名或密码不正确";
            }
            return "Login Success!";
        } catch (Exception e) {
            return "登陆时候发生异常," + e.getMessage();
        }
    }

    @GetMapping("/user/hello.do")
    public String hello() {
        return "Hello User, From Server";
    }

    @GetMapping("/admin/hello.do")
    public String helloAdmin() {
        return "Hello Admin, From Server";
    }

    @GetMapping("/welcome.do")
    public String loginSuccess() {
        return "welcome";
    }

    @GetMapping("/403.do")
    public Object error403(HttpServletResponse response) {
        response.setStatus(403);
        JSONObject object = new JSONObject();
        object.put("message", "用户权限不够");
        return object;
    }

}
