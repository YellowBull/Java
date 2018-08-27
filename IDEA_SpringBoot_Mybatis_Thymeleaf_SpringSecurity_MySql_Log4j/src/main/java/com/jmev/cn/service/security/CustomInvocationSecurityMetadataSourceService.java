package com.jmev.cn.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.jmev.cn.entity.security.SysRole;
import com.jmev.cn.service.security.curd.SysResourceService;
import com.jmev.cn.service.security.curd.SysRoleService;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，
 * 即getAttributes方法返回的结果。 此类在初始化时，
 * 应该取到所有资源及其对应角色的定义。
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource
{
    @SuppressWarnings("unused")
    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService sysRoleService;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
     * PostConstruct在构造函数之后执行,init()方法之前执行。
     */
    @PostConstruct
    private void loadResourceDefine()
    { //一定要加上@PostConstruct注解
      // 在Web服务器启动时，提取系统中的所有权限。
        Iterable<SysRole> list = sysRoleService.findAll();
        List<String> query = new ArrayList<String>();
        if (list != null)
        {
            for (SysRole sr : list)
            {
                query.add(sr.getName());
            }
        }
        /*
         * 应当是资源为key， 权限为value。 资源通常为url，
         *  权限就是那些以ROLE_为前缀的角色。 
         *  一个资源可以由多个权限来访问。
         * sparta
         */
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (String auth : query)
        {
            ConfigAttribute ca = new SecurityConfig(auth);
            List<String> query1 = new ArrayList<String>();// 登录角色所拥有的全部url权限
            List<String> urls = sysResourceService.findByRoleName(auth);
            if (urls != null && urls.size() > 0)
            {
                for (String url : urls)
                {
                    query1.add(url);
                }
            }
            for (String res : query1)
            {
                String url = res;
                /*
                     * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，
                     * 则要通过该url为key提取出权限集合，将权限增加到权限集合中。
                 * sparta
                 */
                if (resourceMap.containsKey(url))
                {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                } else
                {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
            }
        }
    }

    /**
     * 根据URL，找到相关的权限配置。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
    {
        // object 是一个URL，被用户请求的url。
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null)
        {
            loadResourceDefine();
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext())
        {
            String resURL = ite.next();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
            if (requestMatcher.matches(filterInvocation.getHttpRequest()))
            {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return new ArrayList<ConfigAttribute>();
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }

}
