package com.jmev.cn.service.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *  登录成功处理器
 *      需求：
 *          登录成功后，默认跳转到对应角色下的页面
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException
    {
        //登录成功后跳转到默认对应的页面
        String targetUrl = "/login";
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities())
        {
            String roleName = grantedAuthority.getAuthority();
            switch (roleName)
            {
            case "SUPER":
                targetUrl = "/super";
                break;
            case "ADMIN":
                targetUrl = "/admin";
                break;
            case "VIP":
                targetUrl = "/vip";
                break;
            case "USER":
                targetUrl = "/user";
                break;
            }
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
